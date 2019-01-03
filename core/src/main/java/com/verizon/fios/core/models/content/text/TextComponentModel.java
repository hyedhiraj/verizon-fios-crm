package com.verizon.fios.core.models.content.text;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

/**
 * Model for link list component
 * @author Merkle / Axis41
 */
@Model(adaptables = Resource.class)
public interface TextComponentModel {
   /**
   *
   * @return query background
   */
   @ValueMapValue(name = "background",
            injectionStrategy = InjectionStrategy.OPTIONAL)
   String getBackground();
   /**
   *
   * @return String fontSize
   */
   @ValueMapValue(name = "fontSize",
           injectionStrategy = InjectionStrategy.OPTIONAL)
   String getFontSize();
   /**
   *
   * @return String fontColor
   */
   @ValueMapValue(name = "fontColor",
          injectionStrategy = InjectionStrategy.OPTIONAL)
  	String getFontColor(); 
  	/**
   	*
  	* @return String fontWeight
  	*/
  	@ValueMapValue(name = "fontWeight",
         injectionStrategy = InjectionStrategy.OPTIONAL)
 	String getFontWeight();
  	
  	/**
    *
    * @return String text
    */
  	@ValueMapValue(name = "text",
  		injectionStrategy = InjectionStrategy.OPTIONAL)
  	String getText();
}