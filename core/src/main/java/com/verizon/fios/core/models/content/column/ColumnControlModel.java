package com.verizon.fios.core.models.content.column;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

/**
 * Model for Column Contro Model
 * @author Rauxa
 */
@Model(adaptables = SlingHttpServletRequest.class)
public class ColumnControlModel {
	
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /** columns */
    	@ValueMapValue(name = "columns",
    		injectionStrategy = InjectionStrategy.OPTIONAL)
    String columns;
    	/**percent by columns */
    	String percentage;
    	/**list of columns */
    	List<String> lstColumns;

    /**
     * init method
     */
    @PostConstruct
    private void init() {
	    	if(logger.isDebugEnabled())logger.debug(" Start LinkListComponentModel ");
	    if (columns != null) {
	    		lstColumns = new ArrayList<>();
	    		for(int count=0; count<new Integer(columns); count++) {
	    			lstColumns.add(count+"");
	    		}
	    		percentage = (new Integer(100/new Integer(columns))).toString();
	    }
    }
	/**
	 * getter for columns
	 * @return number of columns
	 */
	public String getColumns() {
		return columns;
	}
	/**
	 * getter for percentage
	 * @return percentage of columns
	 */
	public String getPercentage() {
		return percentage;
	}
	/**
	 * getter for lstColumns
	 * @return lstColumns of columns
	 */
	public List<String> getLstColumns() {
		return lstColumns;
	}

}