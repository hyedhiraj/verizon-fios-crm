package com.verizon.fios.core.models.content.link_list;

import com.verizon.fios.core.models.content.LinkModel;
import com.verizon.fios.core.services.util.LinkUtils;
import com.day.cq.wcm.api.Page;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Model for link list component
 * @author Merkle / Axis41
 */
@Model(adaptables = SlingHttpServletRequest.class)
public class LinkListComponentModel {
	
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /** links */
    @ChildResource(name = "links",
            injectionStrategy = InjectionStrategy.OPTIONAL)
    List<LinkModel> links;

    /** title */
//    @ChildResource(name = "title",
//            injectionStrategy = InjectionStrategy.OPTIONAL)
//    GlobalTitleComponentModel title;

    /** resourceResolver */
    @SlingObject
    private ResourceResolver resourceResolver;

    /** linkListItems */
    private List<Map<String, String>> linkListItems;

    /** path */
    private String titleRes;

    /**
     * init method
     */
    @PostConstruct
    private void init() {
    	if(logger.isDebugEnabled())logger.debug(" Start LinkListComponentModel ");
        if (links != null) {
        		System.out.println("ooooooooooooooo");
            readLinkListItems(links);
        }
//        if (title != null) {
//            this.titleRes = title.getText();
//        }
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
}