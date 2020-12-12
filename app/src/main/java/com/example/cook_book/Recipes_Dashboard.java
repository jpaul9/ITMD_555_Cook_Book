package com.example.cook_book;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.cook_book.model.Recipe;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Recipes_Dashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
	DrawerLayout drawerLayout;
	ActionBarDrawerToggle toggle;
	NavigationView nav_view;
	RecyclerView recipeLists;
	

	FirebaseFirestore fstore;
	FirestoreRecyclerAdapter<Recipe,RecipeViewHolder> recipeAdapter;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recipes_dashboard);

		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		//pull saved recipes from firebase
		fstore = FirebaseFirestore.getInstance();
		Query query = fstore.collection("recipes").orderBy("title", Query.Direction.DESCENDING);
		FirestoreRecyclerOptions<Recipe> allRecipes = new FirestoreRecyclerOptions.Builder<Recipe>()
				.setQuery(query,Recipe.class)
				.build();

		recipeAdapter = new FirestoreRecyclerAdapter<Recipe, RecipeViewHolder>(allRecipes) {
			@Override
			protected void onBindViewHolder(@NonNull RecipeViewHolder recipeholder, int position, @NonNull Recipe recipe) {
				recipeholder.recipeTitle.setText(recipe.getTitle());
				recipeholder.recipeContent.setText(recipe.getContent());

				recipeholder.mCardView.setCardBackgroundColor(recipeholder.view.getResources().getColor(getRandomColor(),null));
				final String recipeId = recipeAdapter.getSnapshots().getSnapshot(position).getId();

				recipeholder.view.setOnClickListener(v -> {
					Intent intent = new Intent(v.getContext(), RecipeDetails.class);
					intent.putExtra("title",recipe.getTitle());
					intent.putExtra("content",recipe.getContent());
					intent.putExtra("recipeId",recipeId);

					v.getContext().startActivity(intent);

				});
			}

			@NonNull
			@Override
			public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
				View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_view_layout,parent,false);
				return new RecipeViewHolder(view);
			}
		};


		drawerLayout = findViewById(R.id.drawer);
		nav_view = findViewById(R.id.nav_view);
		nav_view.setNavigationItemSelectedListener(this);

		recipeLists = findViewById(R.id.recipelist);

		toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
		drawerLayout.addDrawerListener(toggle);
		toggle.setDrawerIndicatorEnabled(true);
		toggle.syncState();

		recipeLists.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
		recipeLists.setAdapter(recipeAdapter);

		FloatingActionButton fab = findViewById(R.id.addRecipeFloat);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				startActivity(new Intent(view.getContext(), AddRecipe.class));
			}
		});
	}


	@Override
	public boolean onNavigationItemSelected(@NonNull MenuItem item) {
		switch (item.getItemId()){
			case R.id.addrecipe:
				startActivity(new Intent(this, AddRecipe.class));
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

	public class RecipeViewHolder extends RecyclerView.ViewHolder{

		TextView recipeTitle,recipeContent;
		View view;
		CardView mCardView;

		public RecipeViewHolder(@NonNull View itemView) {
			super(itemView);

			recipeTitle = itemView.findViewById(R.id.titles);
			recipeContent = itemView.findViewById(R.id.content);
			view = itemView;
			mCardView = itemView.findViewById(R.id.recipeCard);
		}
	}
	private int getRandomColor() {
		List<Integer> colorCode = new ArrayList<>();
		colorCode.add(R.color.blue);
		colorCode.add(R.color.yellow);
		colorCode.add(R.color.lightPurple);
		colorCode.add(R.color.lightGreen);
		colorCode.add(R.color.pink);
		colorCode.add(R.color.skyblue);
		colorCode.add(R.color.gray);

		Random randomColor = new Random();
		int number = randomColor.nextInt(colorCode.size());
		return colorCode.get(number);
	}

	@Override
	protected void onStart() {
		super.onStart();
		recipeAdapter.startListening();
	}

	@Override
	protected void onStop() {
		super.onStop();
		if (recipeAdapter != null) {
			recipeAdapter.stopListening();
		}
	}

}