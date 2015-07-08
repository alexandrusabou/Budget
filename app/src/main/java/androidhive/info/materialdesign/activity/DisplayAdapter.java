package androidhive.info.materialdesign.activity;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidhive.info.materialdesign.R;

/**
 * adapter to populate listview with data
 * @author ketan(Visit my <a
 *         href="http://androidsolution4u.blogspot.in/">blog</a>)
 */
public class DisplayAdapter extends BaseAdapter {
    private Context mContext;
    //private ArrayList<String> id;
    //private ArrayList<String> firstName;
    //private ArrayList<String> lastName;
    private ArrayList<String> id;
    private ArrayList<String> idCont;
    private ArrayList<String> numeCont;
    private ArrayList<String> codBuget;

    //public DisplayAdapter(Context c, ArrayList<String> id,ArrayList<String> fname, ArrayList<String> lname) {
    public DisplayAdapter(Context c, ArrayList<String> id, ArrayList<String> idC, ArrayList<String> nCont,
                          ArrayList<String> cBuget){
        this.mContext = c;
        this.id = id;
        this.idCont = idC;
        this.numeCont = nCont;
        this.codBuget=cBuget;
    }

    public int getCount() {
        // TODO Auto-generated method stub
        return id.size();
    }

    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    public View getView(int pos, View child, ViewGroup parent) {
        Holder mHolder;
        LayoutInflater layoutInflater;
        if (child == null) {
            layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            child = layoutInflater.inflate(R.layout.listcell, null);
            mHolder = new Holder();
            mHolder.txt_id = (TextView) child.findViewById(R.id.txt_id);
            mHolder.txt_id_cont = (TextView) child.findViewById(R.id.txt_id_cont);
            mHolder.txt_nume_cont = (TextView) child.findViewById(R.id.txt_nume_cont);
            mHolder.txt_cod_buget = (TextView) child.findViewById(R.id.txt_cod_buget);

            child.setTag(mHolder);
        } else {
            mHolder = (Holder) child.getTag();
        }
        mHolder.txt_id.setText(id.get(pos));
        mHolder.txt_id_cont.setText(idCont.get(pos));
        mHolder.txt_nume_cont.setText(numeCont.get(pos));
        mHolder.txt_cod_buget.setText(codBuget.get(pos));


        return child;
    }

    public class Holder {
        TextView txt_id;
        TextView txt_id_cont;
        TextView txt_nume_cont;
        TextView txt_cod_buget;
    }

}
