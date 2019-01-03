package com.verizon.fios.core.models.content.spotlight;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

/**
 * Model for link list component
 * @author Merkle / Axis41
 */
@Model(adaptables = Resource.class)
public interface TextSpotlightComponentModel {
   /**
   *
   * @return query textArea
   */
   @ValueMapValue(name = "textArea",
            injectionStrategy = InjectionStrategy.OPTIONAL)
   String getTextArea();
   /**
   *
   * @return String componentType
   */
   @ValueMapValue(name = "componentType",
           injectionStrategy = InjectionStrategy.OPTIONAL)
   String getComponentType();
   /**
   *
   * @return String altLogoImage
   */
   @ValueMapValue(name = "altLogoImage",
          injectionStrategy = InjectionStrategy.OPTIONAL)
  	String getAltLogoImage(); 
	/**
	*
	* @return textImage String
	*/
  	@ValueMapValue(name = "image/fileLogoReference",
         injectionStrategy = InjectionStrategy.OPTIONAL)
 	String getFileLogoReference();
  	
  	/**
   	*
  	* @return String linkText
  	*/
  	@ValueMapValue(name = "label",
         injectionStrategy = InjectionStrategy.OPTIONAL)
 	String getLabel();
  	/**
   	*
  	* @return String pathField
  	*/
  	@ValueMapValue(name = "link",
         injectionStrategy = InjectionStrategy.OPTIONAL)
 	String getLink();
}
