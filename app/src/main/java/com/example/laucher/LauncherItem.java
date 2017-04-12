package com.example.laucher;

import java.io.Serializable;

public class LauncherItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int id;
	public int type;//0表示需要验证登录，1表示不需要验证登录
	public int iconId;
	public int smallIconId;
	public int textId;
	public boolean isCloud = false;//是否有气泡
	public int cloudCount = 0;
	public Class<?> cls;
	public boolean isShow = false;
	private boolean none = false;

	public LauncherItem() {
		this.none = true;
	}

	public LauncherItem(int id, int type, int iconId, int smallIconId,
			int textId, Class<?> cls) {
		this.id = id;
		this.type = type;
		this.iconId = iconId;
		this.smallIconId = smallIconId;
		this.textId = textId;
		this.isCloud = false;
		this.cls = cls;
		this.none = false;
	}

	public void none() {
		this.none = true;
	}

	public boolean isNone() {
		return this.none;
	}

}
