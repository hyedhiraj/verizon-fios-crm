package com.verizon.fios.core.models.content.main_nav;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.api.Page;
import com.verizon.fios.core.models.content.LinkModel;
import com.verizon.fios.core.services.util.LinkUtils;


/**
 * Model for link list component
 * @author Merkle / Axis41
 */
@Model(adaptables = SlingHttpServletRequest.class)
public class MainNavComponentModel {
	
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /** links */
    @ChildResource(name = "links",
            injectionStrategy = InjectionStrategy.OPTIONAL)
    List<LinkModel> links;

    /** title */
    @ChildResource(name = "fonts",
            injectionStrategy = InjectionStrategy.OPTIONAL)
    String fontStyle;

    /** resourceResolver */
    @SlingObject
    private ResourceResolver resourceResolver;

    /** linkListItems */
    private List<Map<String, String>> linkListItems;

    /** path */
    private String titleRes;
    
    /**
    *
    * @return fileReference
    */
   @ValueMapValue(name = "image/fileReference",
           injectionStrategy = InjectionStrategy.OPTIONAL)
   String fileReference;
   /**
    *
    * @return fileReferenceMobile
    */
   @ValueMapValue(name = "imageMobile/fileReference",
           injectionStrategy = InjectionStrategy.OPTIONAL)
   String fileReferenceMobile;

    /**
     * init method
     */
    @PostConstruct
    private void init() {
    	if(logger.isDebugEnabled())logger.debug(" Start LinkListComponentModel ");
        if (links != null) {
            readLinkListItems(links);
        }
    }
    /**
     * Method readLinkListItems
     */
    private void readLinkListItems(final List<LinkModel> links) {
        linkListItems = new ArrayList<>();
        String linkHref;
        String linkLabel;
        String linkTarget;
        String linkQuery;
        String href;
        String borderBottom;
        for (LinkModel childLink: links) {
            Map<String, String> map = new HashMap<>();
            linkLabel   = childLink.getLabel() != null ? childLink.getLabel() : "";
            linkHref   = childLink.getHref()   != null ? childLink.getHref() : "";
            linkTarget = childLink.getTarget() != null ? childLink.getTarget() : "";
            linkQuery = childLink.getQuery() != null ? childLink.getQuery() : "";
            borderBottom = childLink.getBorderBottom() == true ? "link-list-border-bottom":"";
            href = LinkUtils.formatLink(linkHref, resourceResolver);
            map.put("label", getTextFromResource(linkHref,  linkLabel));
            map.put("href", href);
            map.put("query", linkQuery);
            map.put("target", linkTarget);
            map.put("borderBottom", borderBottom);
            linkListItems.add(map);
        }
    }
    /**
     * Method to find text for a link
     * @param link link
     * @param text text description
     * @return text validation
     */
    private String getTextFromResource(final String link, final String text) {
        String textResult = "";
        Resource resourceLink = resourceResolver.getResource(link);
        if (StringUtils.isNotBlank(text)) {
            textResult = text;
        } else if (resourceLink != null ) {
            Page page = resourceLink.adaptTo(Page.class);
            if (page != null && page.getTitle() != null) {
                textResult = page.getTitle();
            } else if (page != null && page.getName() != null) {
                textResult = page.getName();
            } else if (page != null && page.getPageTitle() != null) {
                textResult = page.getPageTitle();
            }
        } else {
            textResult = "[page missing]";
        }
        return textResult;
    }
    /**
     * @return titleRes
     */
	public String getTitleRes() {
		return titleRes;
	}
    /**
     * @return links
     */
	public List<LinkModel> getLinks() {
		return links;
	}
    /**
     * @return linkListItems
     */
    public List<Map<String, String>> getLinkListItems() {
        return linkListItems;
    }
    /**
     * @return fileReference
     */
	public String getFileReference() {
		return fileReference;
	}
    /**
     * @return fileReferenceMobile
     */
	public String getFileReferenceMobile() {
		return fileReferenceMobile;
	}
    /**
     * @return fontStyle
     */
	public String getFontStyle() {
		return fontStyle;
	}
}