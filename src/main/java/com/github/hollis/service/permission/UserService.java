package com.github.hollis.service.permission;

import com.github.hollis.dao.entity.RoleUserEntity;
import com.github.hollis.dao.entity.UserEntity;
import com.github.hollis.dao.repository.UserRepository;
import com.github.hollis.domain.dto.base.PageRequest;
import com.github.hollis.domain.dto.permission.CreateUserDto;
import com.github.hollis.domain.dto.permission.QueryUserDto;
import com.github.hollis.domain.dto.permission.UpdateUserDto;
import com.github.hollis.domain.vo.permission.UserVo;
import com.github.hollis.event.EventUtils;
import com.github.hollis.event.UserForceLogoutEvent;
import com.github.hollis.mapper.UserMapper;
import com.github.hollis.utils.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleUserService roleUserService;

    /**
     * 通过loginId查询用户信息
     *
     * @param loginId
     * @return
     */
    public Optional<UserEntity> getByLoginId(String loginId) {
        return Optional.ofNullable(userRepository.findByLoginIdAndDeleteAtIsNull(loginId));
    }

    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }

    public List<UserEntity> findByRoleId(Integer roleId) {
        return userRepository.findByRoleId(roleId);
    }

    /**
     * 添加用户
     *
     * @param reqDto
     */
    @Transactional
    public void addUser(CreateUserDto reqDto) {
        //校验loginID
        if (this.getByLoginId(reqDto.getLoginId()).isPresent()) {
            throw new IllegalArgumentException("Login ID 已存在");
        }
        Integer operationId = UserUtil.getCurrentUserId();
        String encodedPwd = passwordEncoder.encode(reqDto.getPassword());
        UserEntity newUser = reqDto.newUser(encodedPwd, operationId);
        userRepository.save(newUser);
        if (!CollectionUtils.isEmpty(reqDto.getBindingRoles())) {
            roleUserService.saveUserRole(newUser.getId(), reqDto.getBindingRoles(), operationId);
        }
    }


    /**
     * 更新用户信息
     * @param reqDto
     */
    public void updateUser(UpdateUserDto reqDto) {
        UserEntity originUser = userRepository.findById(reqDto.getId()).orElseThrow(() -> new IllegalArgumentException("用户不存在"));
        userRepository.save(reqDto.convert4Update(originUser, UserUtil.getCurrentUserId()));
        //更新用户角色关联表
        roleUserService.saveUserRole(reqDto.getId(), reqDto.getBindingRoles(), UserUtil.getCurrentUserId());
    }

    @Transactional
    public void deleteUser(Integer userId) {
        List<Integer> deleteRoleUserList = roleUserService.findByUserId(userId)
                .stream()
                .map(RoleUserEntity::getId)
                .collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(deleteRoleUserList)) {
            roleUserService.deleteByIds(deleteRoleUserList);
        }
        userRepository.logicDelete(userId, UserUtil.getCurrentUserId());
        EventUtils.publish(new UserForceLogoutEvent(userId));
    }

    public void disableUser(Integer userId) {
        userRepository.findById(userId)
                .ifPresent(user -> {
                    user.setStatus((byte) 0);
                    user.setUpdateBy(UserUtil.getCurrentUserId());
                    userRepository.save(user);
                    EventUtils.publish(new UserForceLogoutEvent(userId));
                });
    }


    public void enableUser(Integer userId) {
        userRepository.findById(userId)
                .ifPresent(user -> {
                    user.setStatus((byte) 1);
                    user.setUpdateBy(UserUtil.getCurrentUserId());
                    userRepository.save(user);
                });
    }

    public Page<UserEntity> queryUserByPage(QueryUserDto dto) {
        return userRepository.findAll(dto.toSpecification(), dto.toPageable());
    }
}
