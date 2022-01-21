package com.offcn.order.vo.req;

import lombok.Data;

@Data
public class OrderInfoVo {

    private String accessToken;

    private Integer projectid; // 项目id

    private Integer returnid; // 回报id

    private Integer rtncount; // 回报数量

    private String address; // 收获地址

    private String invoice; // 是否开发票

    private String invoictitle; // 发票头

    private String remark; // 备注

}
