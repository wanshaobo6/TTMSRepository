package com.ttms.Vo;

import com.ttms.Entity.ProGroup;
import lombok.Data;

@Data
public class GroupManageVo extends ProGroup {
    //负责人姓名
    private String chargerName;

    //负责人电话号码
    private String chargerPhoneNumber;

    //所属项目名
    private String projectName;
}
