package com.example.cook_book.add;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.cook_book.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddRecipe extends AppCompatActivity {

	FirebaseFirestore fstore;
	EditText contentTitle,recipeContent;
	ProgressBar saveRecipeProgBar;
	FirebaseUser user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_recipe);
		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		fstore = FirebaseFirestore.getInstance();
		user = FirebaseAuth.getInstance().getCurrentUser();
		contentTitle = findViewById(R.id.addRecipeTitle);
		recipeContent = findViewById(R.id.addRecipeContent);
		saveRecipeProgBar = findViewById(R.id.progressBar);

		FloatingActionButton fab = findViewById(R.id.saveEditedRecipe);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				String rTitle = contentTitle.getText().toString();
				String rContent = recipeContent.getText().toString();

				if(rTitle.isEmpty() || rContent.isEmpty()){

					Toast.makeText(AddRecipe.this, "Cannot save empty Recipe entry", Toast.LENGTH_SHORT).show();
					return;
				}
				saveRecipeProgBar.setVisibility(View.VISIBLE);

				// save recipe to firebase
				DocumentReference docref= fstore.collection("recipes").document(user.getUid()).collection("My Recipes").document();
				Map<String,Object> recipe = new HashMap<>();
				recipe.put("title",rTitle);
				recipe.put("content",rContent);

				docref.set(recipe).addOnSuccessListener(new OnSuccessListener<Void>() {
					@Override
					public void onSuccess(Void aVoid) {
						Toast.makeText(AddRecipe.this, "Recipe Saved", Toast.LENGTH_SHORT).show();
						onBackPressed();
					}
				}).addOnFailureListener(new OnFailureListener() {
					@Override
					public void onFailure(@NonNull Exception e) {
						Toast.makeText(AddRecipe.this, "Recipe not Saved. Please try again", Toast.LENGTH_SHORT).show();
						saveRecipeProgBar.setVisibility(View.INVISIBLE);
					}
				});

			}
		});
	}
}