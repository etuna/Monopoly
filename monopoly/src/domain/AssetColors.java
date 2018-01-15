package domain;

import java.io.Serializable;

public class AssetColors  implements Serializable{
	public static AssetColors assetColors;

	
	public static AssetColors getInstance() {
		if(assetColors == null) {
			assetColors = new AssetColors();
		}
		return assetColors;
	}
	
	
	
	
}
