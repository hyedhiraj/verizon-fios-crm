package com.verizon.fios.core.services.util;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.commons.Externalizer;
import com.day.cq.dam.api.Asset;
import com.day.cq.wcm.api.Page;

/**
 * Utils for links
 * Model for link list component
 * @author Merkle / Axis41
 */
public class LinkUtils {
	/** logger */
	protected static final Logger logger = LoggerFactory.getLogger(LinkUtils.class);
	/**
	 * format to the path finding the correct page resource
	 * @param linkStr
	 * @param resourceResolver
	 * @return
	 */
	public static String formatLink(String linkStr, ResourceResolver resourceResolver) {
		if (linkStr == null || linkStr.length() < 2) {
			return "";
		} else if (!linkStr.contains("/content/") && !linkStr.startsWith("http")) {
			return "http://" + linkStr;
		}

		Resource resource = resourceResolver.getResource(linkStr);
		if (resource != null) {
			Page page = resource.adaptTo(Page.class);
			Asset asset = resource.adaptTo(Asset.class);
			if (page != null) {
				linkStr = page.getPath() + ".html";
			} else if (asset != null) {
				linkStr = asset.getPath();
			} 
		}
		return linkStr;
	}

	/**
	 * Externalizing an url
	 * @param url
	 * @param externalizer
	 * @param resourceResolver
	 * @return
	 */
	public static String externalizeUrl(String url, Externalizer externalizer, ResourceResolver resourceResolver) {
		if(externalizer.equals(null) || resourceResolver.equals(null)) {
			LinkUtils.logger.info("Url cannot be externalized because resourceResolver or externalizer is null", url);
			return url;
		}
		return externalizer.publishLink(resourceResolver, url);
	}
}
