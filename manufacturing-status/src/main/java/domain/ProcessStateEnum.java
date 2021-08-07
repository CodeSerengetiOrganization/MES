package domain;

/**
 * @author `<a href="mailto:qiang.wang@1020@gmail.com">qiang</a>`
 * @date 2020-11-15
 * @description :
 */
public enum ProcessStateEnum {
        INIT("init",0),
        LOAD_OK("load_ok",1),
        LOAD_NG("load_ng",2),
    COAT_OK("coat_ok",5),
    COAT_NG("coat_ng",6),
    CUT_OK("cut_ok",3),
    CUT_NG("cut_ng",4),
    ASSEMBLE_OK("assemble_ok",7),
    ASSEMBLE_NG("assemble_ng",8),
    EOL_OK("load_ok",9),
    EOL_NG("load_ng",10),
    PACKAGE_OK("load_ok",11),
    PACKAGE_NG("load_ng",12);



    private Integer code;
    private String name;

    ProcessStateEnum(String name, Integer code){
        this.name=name;
        this.code=code;
    }//constructor
}
