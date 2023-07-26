package com.a2m.gen.models.iqTest;

import com.a2m.gen.entities.iqTest.AemIqTestMap;
import com.a2m.gen.models.ParamBaseModel;

/**
 *
 * @author phongnc
 */
public class IqTestQuesMapModel extends ParamBaseModel {

    private IqTestModel iqTestModel;

    private IqTestQuesModel iqTestQuesModel;
    
    
    public IqTestQuesMapModel() {
        super();
        // TODO Auto-generated constructor stub
    }

    public IqTestQuesMapModel(AemIqTestMap db) {
        super();
        this.iqTestModel = new IqTestModel(db.getAemIqTest());
        this.iqTestQuesModel = new IqTestQuesModel(db.getiqTestQuesEntity());
    }

    public IqTestModel getIqTestModel() {
        return iqTestModel;
    }

    public void setIqTestModel(IqTestModel iqTestModel) {
        this.iqTestModel = iqTestModel;
    }

    public IqTestQuesModel getIqTestQuesModel() {
        return iqTestQuesModel;
    }

    public void setIqTestQuesModel(IqTestQuesModel iqTestQuesModel) {
        this.iqTestQuesModel = iqTestQuesModel;
    }

}
