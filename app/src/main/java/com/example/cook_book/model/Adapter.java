package com.example.cook_book.model;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cook_book.R;
import com.example.cook_book.RecipeDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
	List<String> titles;
	List<String> content;

	public Adapter(List<String> titles,List<String> content){
		this.titles = titles;
		this.content = content;

	}

	//this will create the view for the recycler view where the data will be created
	@NonNull
	@Override
	public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_view_layout,parent,false);
		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
		holder.recipeTitle.setText(titles.get(position));
		holder.recipeContent.setText(content.get(position));

		holder.mCardView.setCardBackgroundColor(holder.view.getResources().getColor(getRandomColor(),null));

		holder.view.setOnClickListener(v -> {
			Intent intent = new Intent(v.getContext(), RecipeDetails.class);
			intent.putExtra("title",titles.get(position));
			intent.putExtra("content",content.get(position));
			v.getContext().startActivity(intent);

		});
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
	public int getItemCount() {
		return titles.size();
	}

	public class ViewHolder extends RecyclerView.ViewHolder {
		TextView recipeTitle,recipeContent;
		View view;
		CardView mCardView;



		public ViewHolder(@NonNull View itemView) {
			super(itemView);
			recipeTitle = itemView.findViewById(R.id.titles);
			recipeContent = itemView.findViewById(R.id.content);
			view = itemView;
			mCardView = itemView.findViewById(R.id.recipeCard);

		}
	}
}
