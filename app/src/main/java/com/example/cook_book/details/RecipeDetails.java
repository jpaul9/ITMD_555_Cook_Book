package com.example.cook_book.details;

import android.content.Intent;
import android.os.Bundle;

import com.example.cook_book.R;
import com.example.cook_book.edit.EditRecipe;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class RecipeDetails extends AppCompatActivity {
	Intent data;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recipe_details);
		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		 data = getIntent();


		//this will enable scrolling on the textview for recipes
		TextView content = findViewById(R.id.recipeDetailsContent);
		content.setMovementMethod(new ScrollingMovementMethod());

		TextView title = findViewById(R.id.recipeDetailsTitle);

		content.setText(data.getStringExtra("content"));
		title.setText(data.getStringExtra("title"));


		FloatingActionButton fab = findViewById(R.id.saveEditedRecipe);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(view.getContext(), EditRecipe.class);
				intent.putExtra("title",data.getStringExtra("title"));
				intent.putExtra("content",data.getStringExtra("content"));
				intent.putExtra("recipeId",data.getStringExtra("recipeId"));
				startActivity(intent);
				finish();
			}
		});
	}

	@Override
	public boolean onOptionsItemSelected(@NonNull MenuItem item) {
		if (item.getItemId()==android.R.id.home){
			onBackPressed();
		}
		return super.onOptionsItemSelected(item);
	}
}