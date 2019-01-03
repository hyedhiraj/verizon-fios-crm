package com.verizon.fios.core.models.content.image;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.Via;

/**
 * Model for Image
 * 
 * @author Rauxa
 */
@Model(adaptables = { Resource.class, SlingHttpServletRequest.class })
public class ImageModel {

	/** request var. */
	private SlingHttpServletRequest slingHttpServletRequest;

	/**
	 * fileReference variable
	 */
	@Inject
	@Via("resource")
	@Optional
	private String fileReference;

	/**
	 * jcr:description variable
	 */
	@Inject
	@Via("resource")
	@Named("jcr:description")
	@Optional
	private String description;

	/**
	 * jcr:altImage variable
	 */
	@Inject
	@Via("resource")
	@Named("jcr:alt")
	@Optional
	private String alt;

	/**
	 * href variable
	 */
	@Inject
	@Via("resource")
	@Named("jcr:href")
	@Optional
	private String href;

	/**
	 * alignment variable
	 */
	@Inject
	@Via("resource")
	@Named("alignment")
	@Optional
	private String alignment;

	/**
	 * pathDomain variable
	 */
	private String pathDomain;

	/**
	 * Constructor.
	 * 
	 * @param slingHttpServletRequest
	 *            parameter.
	 */
	public ImageModel(SlingHttpServletRequest slingHttpServletRequest) {
		this.slingHttpServletRequest = slingHttpServletRequest;
	}

	/**
	 * Method init.
	 */
	@PostConstruct
	protected void init() {
		String schema = slingHttpServletRequest.getScheme();
		String getServerName = slingHttpServletRequest.getServerName();
		if (getServerName.equals("localhost")) {
			int port = slingHttpServletRequest.getServerPort();
			this.pathDomain = schema + "://" + getServerName + ":" + port;
		} else {
            this.pathDomain = schema + "://pub." + getServerName;
		}
	}

	/**
	 *
	 * @return fileReference
	 */
	public String getFileReference() {
		return fileReference;
	}

	/**
	 *
	 * @return description String
	 */
	public String getDescription() {
		return description;
	}

	/**
	 *
	 * @return pathDomain String
	 */
	public String getPathDomain() {
		return pathDomain;
	}

	/**
	 *
	 * @return altImage String
	 */
	public String getAlt() {
		return alt;
	}

	/**
	 *
	 * @return href String
	 */
	public String getHref() {
		return href;
	}

	/**
	 *
	 * @return alignment String
	 */
	public String getAlignment() {
		return alignment;
	}

}