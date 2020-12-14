package com.example.cook_book.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.cook_book.add.AddRecipe;
import com.example.cook_book.edit.EditRecipe;
import com.example.cook_book.R;
import com.example.cook_book.details.RecipeDetails;
import com.example.cook_book.login.Login_options;
import com.example.cook_book.model.Recipe;
import com.example.cook_book.registration.Registration;
import com.example.cook_book.splash.Splash_Screen;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
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
	TextView user_info;
	

	FirebaseFirestore fstore;
	FirebaseAuth fAuth;
	FirebaseUser user;
	FirestoreRecyclerAdapter<Recipe,RecipeViewHolder> recipeAdapter;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recipes_dashboard);

		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		user_info = findViewById(R.id.user_info);

		//pull saved recipes from firebase
		fstore = FirebaseFirestore.getInstance();
		fAuth = FirebaseAuth.getInstance();
		user = fAuth.getCurrentUser();





		Query query = fstore.collection("recipes").document(user.getUid()).collection("My Recipes").orderBy("title", Query.Direction.DESCENDING);
		//query recipes > uuid >user specific recipes> all recipes


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

				//this is responsible for the pop out menu for each entry
				ImageView menuIcon = recipeholder.view.findViewById(R.id.menuIcon);
				menuIcon.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {


						final String recipeId = recipeAdapter.getSnapshots().getSnapshot(position).getId();

						PopupMenu menu = new PopupMenu(v.getContext(),v);

						menu.setGravity(Gravity.END);

						menu.getMenu().add("Edit").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
							@Override
							public boolean onMenuItemClick(MenuItem item) {
								Intent intent = new Intent(v.getContext(), EditRecipe.class);
								intent.putExtra("title",recipe.getTitle());
								intent.putExtra("content",recipe.getContent());
								intent.putExtra("recipeId",recipeId);
								startActivity(intent);
								return false;
							}
						});

						menu.getMenu().add("Delete").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
							@Override
							public boolean onMenuItemClick(MenuItem item) {
								DocumentReference docref = fstore.collection("recipes").document(recipeId);
								docref.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
									@Override
									public void onSuccess(Void aVoid) {
										Toast.makeText(Recipes_Dashboard.this, "Deleted", Toast.LENGTH_SHORT).show();
									}
								}).addOnFailureListener(new OnFailureListener() {
									@Override
									public void onFailure(@NonNull Exception e) {
										Toast.makeText(Recipes_Dashboard.this, "Error. Not deleted", Toast.LENGTH_SHORT).show();
									}
								});
								return false;
							}
						});

						menu.show();

					}
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
		drawerLayout.closeDrawer(GravityCompat.START);
		switch (item.getItemId()){
			case R.id.addrecipe:
				startActivity(new Intent(this, AddRecipe.class));
				finish();
				break;
			case R.id.logout:
					checkUser();
					break;
			case R.id.syncrecipes:
				if (user.isAnonymous()) {
					startActivity(new Intent(this, Login_options.class));

				}else{
					Toast.makeText(this, "Synced", Toast.LENGTH_SHORT).show();
				}

				break;

			default:
				Toast.makeText(this, "Under Construction", Toast.LENGTH_SHORT).show();
				break;
		}
		return false;
	}

	private void checkUser() {
		//verify if is anonymous or registered
		if (FirebaseAuth.getInstance().getCurrentUser().isAnonymous()){

			AlertDialog.Builder warning = new AlertDialog.Builder(this)
					.setTitle("Are you Sure?")
					.setMessage("Thank you for trying our App.\nPlease register to save any entries you have made.")
					.setPositiveButton("Register now", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							Intent intent = new Intent(Recipes_Dashboard.this, Registration.class);
							startActivity(intent);
							finish();
						}
					}).setNegativeButton("Leave", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							//delete any anonymous notes created

							//delete anonymous user
							user.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
								@Override
								public void onSuccess(Void aVoid) {

									Toast.makeText(Recipes_Dashboard.this, "Come again", Toast.LENGTH_SHORT).show();
									Intent intent = new Intent(Recipes_Dashboard.this, Splash_Screen.class);
									startActivity(intent);
									finish();
								}
							});
						}
					});
			warning.show();

		}
		else{
			FirebaseAuth.getInstance().signOut();
			Intent intent = new Intent(Recipes_Dashboard.this, Splash_Screen.class);
			startActivity(intent);
			finish();
		}

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