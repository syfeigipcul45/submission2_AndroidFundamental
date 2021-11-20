package com.example.submission2_githubuser.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class UserSearch{

	@SerializedName("items")
	private List<ItemsItem> items;

	public List<ItemsItem> getItems(){
		return items;
	}
}