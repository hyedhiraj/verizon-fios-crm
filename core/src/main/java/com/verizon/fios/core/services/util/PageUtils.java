package com.verizon.fios.core.services.util;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;

/**
 * @author Merkle / Axis41
 */
public class PageUtils {

    public static Page getSiteRoot(Page currentPage) {
        return currentPage.getAbsoluteParent(1);
    }

    public static Page getLanguageRoot(Page currentPage, PageManager pageManager) {
        Page languageRoot = currentPage !=null ?currentPage.getAbsoluteParent(2):null;
//        if (languageRoot == null) {
//            Page siteRoot = getSiteRoot(currentPage);
//            languageRoot = pageManager.getPage(siteRoot.getPath() + "/en_ca");
//        }
        return languageRoot;
    }
    
    public static String getTitle(Page page){
    	String title = page.getTitle();
    	if(page.getNavigationTitle() != null && !"".equals(page.getNavigationTitle())){
	    	title = page.getNavigationTitle();
	    }
	    return title;
    }
}