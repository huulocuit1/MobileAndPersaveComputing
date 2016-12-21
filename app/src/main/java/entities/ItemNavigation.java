package entities;

public class ItemNavigation {
	public int itemIcon;
	public String itemName;

	public int item_next;

	public String getItemName() {
		return itemName;
	}

	public ItemNavigation(int itemIcon, String itemName) {
		super();
		this.itemIcon = itemIcon;
		this.itemName = itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public int getItemIcon() {
		return itemIcon;
	}

	public void setItemIcon(int itemIcon) {
		this.itemIcon = itemIcon;
	}

	public int getItem_next() {
		return item_next;
	}

	public void setItem_next(int item_next) {
		this.item_next = item_next;
	}

}
