package com.daicy.koala.structure;

/**
 * Created by amd on 9/9/15.
 */
public interface Column {

    public String getColumnName() ;

    public String getDataType() ;

    public Object getDefaultValue() ;

    public Boolean getIsIndexed() ;

    public Boolean getIsId() ;

    public int getPosition();

    public String getAggType();


    public void setPosition(int position);
}
