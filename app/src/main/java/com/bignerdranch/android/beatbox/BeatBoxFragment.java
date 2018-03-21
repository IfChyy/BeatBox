package com.bignerdranch.android.beatbox;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;

/**
 * Created by Ivo Georgiev (IfChyy)
 * BeatBoxFragment fragment class
 * holding a recycler view with gridlayout spaned to 3 collumns
 *
 *
 */

public class BeatBoxFragment extends Fragment {

    private BeatBox beatBox;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //retain the instance of beatbox to play the sound in both orientations
        //instead of stopping it eg. fragment is not destroyed with the activity
        //it is just passed
        //all instance variables keep tge same values if retained
        setRetainInstance(true);
        //init beatBox class
        beatBox = new BeatBox(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //init the layout
        View view = inflater.inflate(R.layout.fragment_beat_box, container, false);
        //init the recyclerview and set its inner layout as gridlayout with 3 collumns
        RecyclerView recyclerView = view.findViewById(R.id.fragment_beat_box_recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));
        //init the adapter
        SoundAdapter adapter = new SoundAdapter(beatBox.getSounds());
        recyclerView.setAdapter(new SoundAdapter(beatBox.getSounds()));


        return view;
    }


    //if fragment destroyd release beatbox sound
    @Override
    public void onDestroy() {
        super.onDestroy();
        beatBox.release();
    }

    //call the fragment from activity to represent it on the screen
    public static BeatBoxFragment newInstance(){
        return new BeatBoxFragment();
    }

    //--------HOLDER CLASS to hold the grid layout each item and represent it
    public class SoundHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private Button soundButton;
        private Sound mySound;
        //init the constructor for each item in grid
        public SoundHolder(LayoutInflater inflater, ViewGroup container) {
            super(inflater.inflate(R.layout.list_item_sound, container, false));
            //init the button
            soundButton = itemView.findViewById(R.id.list_item_sound_button);
            soundButton.setOnClickListener(this);
        }

        //bind sound to the button
        public void bindSound(Sound sound){
            mySound = sound;
            soundButton.setText(mySound.getName());
        }

        @Override
        public void onClick(View view) {
            beatBox.play(mySound);
        }
    }



    //-----------ADAPTER CLASS FOR THE GRID VIEW OF ITEMS TO REPRESENT THEM IN THE RECYRCLER VIEW

    public class SoundAdapter extends RecyclerView.Adapter<SoundHolder>{
        private List<Sound> sounds;

        //constructore
        public SoundAdapter(List<Sound> sounds){
            this.sounds = sounds;
        }

        //create the view from viewholde of each button
        @Override
        public SoundHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            //create the inflater for each item
            LayoutInflater inflater = LayoutInflater.from(getActivity());
              //inflate soundHolder ( each button holder) into the parent( grid view)
            return new SoundHolder(inflater, parent);
        }

        //bind the date for each button
        @Override
        public void onBindViewHolder(SoundHolder holder, int position) {
            Sound sound = sounds.get(position);
            holder.bindSound(sound);
        }
        //get the number of buttons in the list to rerpesent on the grid view
        @Override
        public int getItemCount() {
            return sounds.size();
        }
    }
}
