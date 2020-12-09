package com.example.cook_book;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.InputFilter;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.NavigableSet;

public class Recipes_Dashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
	DrawerLayout drawerLayout;
	ActionBarDrawerToggle toggle;
	NavigationView nav_view;
	RecyclerView recipeLists;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recipes_dashboard);

		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		drawerLayout = findViewById(R.id.drawer);
		nav_view = findViewById(R.id.nav_view);
		nav_view.setNavigationItemSelectedListener(this);

		recipeLists = findViewById(R.id.recipelist);

		toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
		drawerLayout.addDrawerListener(toggle);
		toggle.setDrawerIndicatorEnabled(true);
		toggle.syncState();

	}


	@Override
	public boolean onNavigationItemSelected(@NonNull MenuItem item) {
		switch (item.getItemId()){
			default:
				Toast.makeText(this,"Coming soon",Toast.LENGTH_LONG).show();
		}
		return false;
	}

	// this will display the menu in the recipe dashboard activity
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.option_menu,menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(@NonNull MenuItem item) {
		if(item.getItemId() ==R.id.settings){
			Toast.makeText(this, "Settings menu is clicked ", Toast.LENGTH_SHORT).show();
		}
		return super.onOptionsItemSelected(item);
	}
}