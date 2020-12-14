package com.example.cook_book.edit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.cook_book.R;
import com.example.cook_book.main.Recipes_Dashboard;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EditRecipe extends AppCompatActivity {

	Intent data;
	EditText editRecipeTitle, editRecipeContent;
	FirebaseFirestore fstore;
	ProgressBar progressBar;
	FirebaseUser user;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_recipe);

		fstore = fstore.getInstance();
		user= FirebaseAuth.getInstance().getCurrentUser();
		data = getIntent();

		editRecipeTitle = findViewById(R.id.editRecipeTitle);
		editRecipeContent = findViewById(R.id.editRecipeContent);

		progressBar = findViewById(R.id.progressBar2);

		String recipeTitle = data.getStringExtra("title");
		String recipeContent= data.getStringExtra("content");

		editRecipeTitle.setText(recipeTitle);
		editRecipeContent.setText(recipeContent);

		FloatingActionButton fab = findViewById(R.id.saveEditedRecipe);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String rTitle = editRecipeTitle.getText().toString();
				String rContent = editRecipeContent.getText().toString();

				if(rTitle.isEmpty() || rContent.isEmpty()){

					Toast.makeText(EditRecipe.this, "Cannot save empty Recipe entry", Toast.LENGTH_SHORT).show();
					return;
				}

				progressBar.setVisibility(View.VISIBLE);

				// save recipe to firebase
				DocumentReference docref= fstore.collection("recipes").document(user.getUid()).collection("My Recipes").document(data.getStringExtra("recipeId"));

				Map<String,Object> recipe = new HashMap<>();
				recipe.put("title",rTitle);
				recipe.put("content",rContent);

				//update recipes successfully, send back to dashboard
				docref.update(recipe).addOnSuccessListener(new OnSuccessListener<Void>() {
					@Override
					public void onSuccess(Void aVoid) {
						Toast.makeText(EditRecipe.this, "Recipe Updated", Toast.LENGTH_SHORT).show();
						startActivity(new Intent(getApplicationContext(), Recipes_Dashboard.class));
						finish();

					}
				}).addOnFailureListener(new OnFailureListener() {
					@Override
					public void onFailure(@NonNull Exception e) {
						Toast.makeText(EditRecipe.this, "Changed not Saved.", Toast.LENGTH_SHORT).show();
						progressBar.setVisibility(View.INVISIBLE);
					}
				});
			}
		});

	}
}