package com.doi.himachal.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.doi.himachal.Modal.ResponsePojo;
import com.doi.himachal.epasshp.R;
import com.doi.himachal.lazyloader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kush.Dhawan
 * @project HPePass
 * @Time 03, 05 , 2020
 */
public class HistoryAdapter extends BaseAdapter implements Filterable {

    private Context context;
    private List<ResponsePojo> news;

    private Filter planetFilter;
    private List<ResponsePojo> origUserList;

    ImageLoader il = new ImageLoader(context);


    /**
     * @param context
     * @param objects
     */
    public HistoryAdapter(Context context, List<ResponsePojo> objects) {
        // super(context, objects);
        this.context = context;
        this.news = objects;
        this.origUserList = objects;
    }


    /**
     * @return
     */
    @Override
    public int getCount() {
        return news.size();
    }


    /**
     * @param position
     * @return
     */
    @Override
    public ResponsePojo getItem(int position) {
        return news.get(position);
    }

    /**
     * @param position
     * @return
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.scan_list, parent, false);

        // events_.setTwodigit_state_code(object.getString("2digit_state_code"));
        ResponsePojo u = news.get(position);
        TextView passno = (TextView) view.findViewById(R.id.passno);
        TextView phone_no = (TextView) view.findViewById(R.id.phone_no);
        TextView serverstatus = (TextView) view.findViewById(R.id.serverstatus);
        ImageView imageView1 = (ImageView)view.findViewById(R.id.imageView1);





            String fnm = "id";
            String PACKAGE_NAME = context.getApplicationContext().getPackageName();
            int imgId = this.context.getApplicationContext().getResources().getIdentifier(PACKAGE_NAME+":drawable/"+fnm , null, null);
            System.out.println("IMG ID :: "+imgId);
            System.out.println("PACKAGE_NAME :: "+PACKAGE_NAME);

            imageView1.setImageBitmap(BitmapFactory.decodeResource(context.getApplicationContext().getResources(),imgId));












        passno.setText(u.getPassNo());
        phone_no.setText(u.getMobileNumbr());
        if(u.isUploaddToServeer()){
            serverstatus.setText("Uploaded");
        }else{
            serverstatus.setText("Not Uploaded");
        }



        return view;
    }

    /**
     *
     */
    public void resetData() {
        news = origUserList;
    }

    @Override
    public Filter getFilter() {
        if (planetFilter == null)
            planetFilter = new HistoryAdapter.PlanetFilter();

        return planetFilter;
    }

    /**
     *
     */
    private class PlanetFilter  extends Filter {



        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            // We implement here the filter logic
            if (constraint == null || constraint.length() == 0) {
                // No filter implemented we return all the list
                results.values = origUserList;
                results.count = origUserList.size();
            }
            else {
                // We perform filtering operation
                List<ResponsePojo> nPlanetList = new ArrayList<>();

                for (ResponsePojo p : news) {
                    if (p.getMobileNumbr().toUpperCase().contains(constraint.toString().toUpperCase()) ||
                            p.getPassNo().toUpperCase().contains(constraint.toString().toUpperCase())   ){
                        nPlanetList.add(p);
                    }//else{
//                        nPlanetList.remove(p);
//                    }

                }

                results.values = nPlanetList;
                results.count = nPlanetList.size();

            }
            return results;
        }


        /**
         * @param constraint
         * @param results
         */
        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {

            // Now we have to inform the adapter about the new list filtered
            if (results.count == 0)
                notifyDataSetInvalidated();
            else {
                news = (List<ResponsePojo>) results.values;
                notifyDataSetChanged();
            }

        }

    }

}