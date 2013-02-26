package se.hv.dindag;

import se.hv.crille.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

/**
 * Fires up the DinDagMenu
 * 
 * @author imcoh
 * 
 */
public class DinDagMenu extends Activity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.menu.menu_start);
	}
}