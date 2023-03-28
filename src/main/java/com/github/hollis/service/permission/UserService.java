package com.github.hollis.service.permission;

import com.github.hollis.dao.entity.RoleUserEntity;
import com.github.hollis.dao.entity.UserEntity;
import com.github.hollis.dao.repository.UserRepository;
import com.github.hollis.domain.dto.permission.CreateUserDto;
import com.github.hollis.mapper.UserMapper;
import com.github.hollis.utils.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
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
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleUserService roleUserService;

    /**
     * 通过loginId查询用户信息
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
     * @param reqDto
     */
    @Transactional
    public void addUser(CreateUserDto reqDto) {
        //校验loginID
        if (this.getByLoginId(reqDto.getLoginId()).isPresent()) {
            throw new IllegalArgumentException("Login ID 已存在");
        }
        Integer userId = UserUtil.getCurrentUserId();
        String encodedPwd = passwordEncoder.encode(reqDto.getPassword());
        UserEntity newUser = reqDto.newUser(encodedPwd, userId);
        userRepository.save(newUser);
        if (!CollectionUtils.isEmpty(reqDto.getBindingRoles())) {
            List<RoleUserEntity> roleUserEntityList = reqDto.getBindingRoles()
                    .stream()
                    .map(roleId -> {
                        RoleUserEntity roleUserEntity = new RoleUserEntity();
                        roleUserEntity.setUserId(newUser.getId());
                        roleUserEntity.setRoleId(roleId);
                        roleUserEntity.setCreateBy(userId);
                        return roleUserEntity;
                    }).collect(Collectors.toList());
            roleUserService.saveAll(roleUserEntityList);
        }
    }

}
