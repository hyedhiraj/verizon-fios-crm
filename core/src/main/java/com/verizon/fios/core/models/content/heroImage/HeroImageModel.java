package com.verizon.fios.core.models.content.heroImage;

import org.apache.sling.models.annotations.Model;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;

import com.verizon.fios.core.models.content.LinkModel;

@Model(adaptables = {Resource.class})
public interface HeroImageModel {

    @ChildResource(name = "link",
                   injectionStrategy = InjectionStrategy.OPTIONAL)
    LinkModel getLink();
    
}
