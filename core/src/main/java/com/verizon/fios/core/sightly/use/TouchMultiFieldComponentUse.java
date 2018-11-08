package com.verizon.fios.core.sightly.use;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.verizon.fios.core.bean.TouchMultiFieldBean;
import com.adobe.cq.sightly.WCMUsePojo;




public class TouchMultiFieldComponentUse extends WCMUsePojo {

	private static final Logger LOGGER = LoggerFactory.getLogger(TouchMultiFieldComponentUse.class);
	private List<TouchMultiFieldBean> submenuItems = new ArrayList<TouchMultiFieldBean>();

	@Override
	public void activate() throws Exception {
		setMultiFieldItems();
	}

	/**
	 * Method to get Multi field data
	 * 
	 * @return submenuItems
	 */
	private List<TouchMultiFieldBean> setMultiFieldItems() {

		try {
			String[] itemsProps = getProperties().get("links", String[].class);
			if (itemsProps != null) {

				JsonParser parser = new JsonParser();
				for (int i = 0; i < itemsProps.length; i++) {
					System.out.println("json"+itemsProps[i]);
					JsonObject jsonObject = parser.parse(itemsProps[i]).getAsJsonObject();
					TouchMultiFieldBean menuItem = new TouchMultiFieldBean();

					String name = jsonObject.get("name").getAsString();
					String path = jsonObject.get("path").getAsString();

					menuItem.setName(name);
					menuItem.setPath(path);
					submenuItems.add(menuItem);
				}
			}
		} catch (Exception e) {
			LOGGER.error("Exception while Multifield data {}", e.getMessage(), e);
		}
		return submenuItems;
	}

	public List<TouchMultiFieldBean> getMultiFieldItems() {
		return submenuItems;
	}
}