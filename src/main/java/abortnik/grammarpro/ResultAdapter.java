package abortnik.grammarpro;

import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ViewHolder> {
    public static final int FADE_ANIMATION = 1000;
    private List<ResultConstructor> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ImageView icon;
        public TextView question, time, correct_sentence;

        public ViewHolder(View view) {
            super(view);
            question = (TextView) view.findViewById(R.id.question);
            time = (TextView) view.findViewById(R.id.time);
            icon = (ImageView) view.findViewById(R.id.icon);
            correct_sentence = (TextView) view.findViewById(R.id.correct_sentence);

        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ResultAdapter(List<ResultConstructor> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ResultAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.test_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        //  holder.mTextView.setText(mDataset[position]);
        ResultConstructor result = mDataset.get(position);
        if(result.question.length() > 38) {
            holder.question.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        }
        holder.question.setText(result.question);
        if(result.correct_sentence.length() > 17) {
            holder.correct_sentence.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        }
        holder.correct_sentence.setText(result.correct_sentence);
        holder.time.setText(result.time);
        holder.icon.setBackgroundResource(result.correct ? R.drawable.correct_icon : R.drawable.bad_icon);

        setFadeAnimation(holder.itemView);

    }
    private void setFadeAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(FADE_ANIMATION);
        view.startAnimation(anim);
    }
    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
