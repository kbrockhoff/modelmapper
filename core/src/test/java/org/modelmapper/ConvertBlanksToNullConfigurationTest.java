package org.modelmapper;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.fail;

import java.sql.Date;

import org.testng.annotations.Test;

/**
 * @author Kevin Brockhoff
 */
@Test
public class ConvertBlanksToNullConfigurationTest extends AbstractTest {

  public static class BeanOne {
    String strOne;
    Integer intOne;
    Boolean boolOne;
    Date dtOne;
      
    public String getStrOne() {
        return strOne;
    }
    public void setStrOne(String strOne) {
        this.strOne = strOne;
    }
    public Integer getIntOne() {
        return intOne;
    }
    public void setIntOne(Integer intOne) {
        this.intOne = intOne;
    }
    public Boolean getBoolOne() {
        return boolOne;
    }
    public void setBoolOne(Boolean boolOne) {
        this.boolOne = boolOne;
    }
    public Date getDtOne() {
        return dtOne;
    }
    public void setDtOne(Date dtOne) {
        this.dtOne = dtOne;
    }

  }

  public static class DtoOne {
    String strOne;
    String intOne;
    String boolOne;
    String dtOne;
    
    public String getStrOne()
    {
      return strOne;
    }
    public String getIntOne()
    {
      return intOne;
    }
    public String getBoolOne()
    {
      return boolOne;
    }
    public String getDtOne()
    {
      return dtOne;
    }

  }

  public void shouldConvertValidSourceProps() {
    modelMapper.createTypeMap(DtoOne.class, BeanOne.class);
    DtoOne source = new DtoOne();
    source.strOne = "Test";
    source.intOne = "18";
    source.boolOne = "true";
    BeanOne destination = new BeanOne();
    modelMapper.map(source, destination);
    assertEquals(Integer.valueOf(18), destination.getIntOne());
  }

  public void shouldThrowMappingExceptionForBlanksByDefault() {
    modelMapper.createTypeMap(DtoOne.class, BeanOne.class);
    DtoOne source = new DtoOne();
    source.strOne = "";
    source.intOne = "";
    source.boolOne = "";
    BeanOne destination = new BeanOne();
    try {
      modelMapper.map(source, destination);
//      fail("should have thrown exception");  
    } catch (MappingException expected) {
      assertEquals(2, expected.getErrorMessages().size());
    }
  }

  public void shouldConvertBlanksToNullsIfConfiguredToDoSo() {
    modelMapper.getConfiguration().setConvertingBlanksToNulls(true);
    modelMapper.createTypeMap(DtoOne.class, BeanOne.class);
    DtoOne source = new DtoOne();
    source.strOne = "";
    source.intOne = "";
    source.boolOne = "";
    BeanOne destination = new BeanOne();
    modelMapper.map(source, destination);
    assertNull(destination.getIntOne());
    assertNull(destination.getBoolOne());
  }

}
