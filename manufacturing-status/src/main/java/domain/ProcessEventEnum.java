package domain;

/**
 * @author `<a href="mailto:qiang.wang@1020@gmail.com">qiang</a>`
 * @date 2020-11-15
 * @description :
 */
public enum ProcessEventEnum {
    LOAD_PASS("load_pass",1),
    LOAD_FAIL("load_fail",2),
    COAT_PASS("coat_pass",3),
    COAT_FAIL("coat_fail",4),
    CUT_PASS("cut_pass",5),
    CUT_FAIL("cut_fail",6),
    ASSEMBLE_PASS("assembl_pass",7),
    ASSEMBLE_FAIL("assemble_fail",8),
    EOL_PASS("eol_pass",9),
    EOL_FAIL("eol_fail",10),
    PACKAGE_PASS("package_pass",11),
    PACKAGE_FAIL("package_fail",12);



    private Integer code;
    private String name;

    ProcessEventEnum(String name, Integer code){
        this.name=name;
        this.code=code;
    }//constructor

}
