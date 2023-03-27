package com.github.hollis.dao.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "t_log")
@Getter
@Setter
public class LogEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "operation_type")
    private String type;

    @Column(name = "operation_target")
    private String target;

    private String content;

    @Column(name = "success_flag")
    private Byte success;

    @Column(name = "exception_info")
    private String exceptionInfo;

    private String parameter;

    private String response;

    @Column(name = "create_at")
    private Date createAt;

    @Column(name = "create_by")
    private Integer createBy;

    @Column(name = "cost_time")
    private Long costTime;

    private String ip;

}
