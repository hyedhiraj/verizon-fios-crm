package com.verizon.fios.core.models.content;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

/**
 * Model for CTALink Component
 * @author Rauxa
 */
@Model(adaptables = Resource.class)
public interface LinkModel {

    /**
     *
     * @return query String
     */
    @ValueMapValue(name = "query",
            injectionStrategy = InjectionStrategy.OPTIONAL)
    String getQuery();

    /**
     *
     * @return text String
     */
    @ValueMapValue(name = "label",
            injectionStrategy = InjectionStrategy.OPTIONAL)
    String getLabel();

    /**
     *
     * @return target String
     */
    @ValueMapValue(name = "target",
            injectionStrategy = InjectionStrategy.OPTIONAL)
    String getTarget();

    /**
     *
     * @return cssClass
     */
    @ValueMapValue(name = "cssClass",
                   injectionStrategy = InjectionStrategy.OPTIONAL)
    String getCssClass();

    /**
     *
     * @return borderBottom
     */
    @ValueMapValue(name = "borderBottom",
            injectionStrategy = InjectionStrategy.OPTIONAL)
    boolean getBorderBottom();
    
    /**
    *
    * @return href String
    */
   @ValueMapValue(name = "href",
           injectionStrategy = InjectionStrategy.OPTIONAL)
   String getHref();

}