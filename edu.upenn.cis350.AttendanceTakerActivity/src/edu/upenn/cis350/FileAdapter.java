package edu.upenn.cis350;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

/**
 * Custom adapter for the ListView used in Roster.
 * Allows multiple pieces of data to be placed in each row of the list
 *
 */
public class FileAdapter extends BaseAdapter implements OnClickListener{
	
	

	private Context context;
	private List<FileObject> filelist;
	CheckBox cstatus;
	boolean isAllCHecked=true;
	int click=0;
	
	//constructor
	public FileAdapter(Context context, List<FileObject> listOffiles){
		this.context = context;
		this.filelist = listOffiles;
	}

	public int getCount() {
		return filelist.size();
	}

	public Object getItem(int position) {
		return filelist.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public FileObject getItems(int position) {
	        return ((FileObject) getItem(position));
	    }
	
	
	public View getView(int position, View convertView, ViewGroup viewGroup){
		//gets entry associated with current row in ListView
		// changed entry -> student_entry
		final FileObject file_entry = filelist.get(position);

		//handles null case
		if(convertView == null){
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.file_item, null);
		}
		// handles Student Name
		// changed variable name 
		TextView textview_StudentName = (TextView) convertView.findViewById(R.id.tvFileItemName);
		textview_StudentName.setText(file_entry.getName());
		cstatus = (CheckBox) convertView.findViewById(R.id.filecheckBox);
		cstatus.setOnCheckedChangeListener(myCheckChangList);

		cstatus.setTag(position);

		cstatus.setChecked(file_entry.box);
		
	
		

		//sets click listener for radio buttons - when clicked, changes status of RosterListItem
//		RadioGroup radios = (RadioGroup) convertView.findViewById(R.id.radio_group1);	 
//		radios.setFocusable(false);
//		radios.setFocusableInTouchMode(false);
//		for(int i=0; i < radios.getChildCount(); i++){  
//			RadioButton btn = (RadioButton) radios.getChildAt(i);  
//			btn.setFocusable(false);
//			btn.setFocusableInTouchMode(false);
//		}
//		radios.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {  
//			public void onCheckedChanged(RadioGroup radiogroup, int checkedId){  
//				for(int i=0; i < radiogroup.getChildCount(); i++){  
//					RadioButton btn = (RadioButton) radiogroup.getChildAt(i);  
//					if(btn.getId() == checkedId){  
//						student_entry.setStatus(btn.getText()+"");                   
//						return;   
//					}  
//				}
//			}
//		});
//		radios.setTag(student_entry);

	
	

//		System.out.println("position1"+isAllCHecked);
		cstatus.setChecked(isAllCHecked);
//		System.out.println("position2"+isAllCHecked);
		return convertView;
	}
	

	//handles button click for specific row in the ListView
	public void onClick(View view) {
		notifyDataSetChanged();
	}
	
	public List<FileObject> getBox() {
	        List<FileObject> box = new ArrayList<FileObject>();
	        for (FileObject i : filelist) {
	            if (i.box)
	                box.add(i);
	        }
	        return box;
	    }
	
	   
	   public void setAllChecked()
	   {
	      isAllCHecked=true;
	      notifyDataSetChanged();
	      
	   } 
	   
	   
	   public void setAllUnChecked()
	   {
		   isAllCHecked=false;
	      notifyDataSetChanged();
	      
	   } 
	   
	  
	 OnCheckedChangeListener myCheckChangList = new OnCheckedChangeListener() {
	        public void onCheckedChanged(CompoundButton buttonView,
	                boolean isChecked) {
	            getItems((Integer) buttonView.getTag()).box = isChecked;
	        }
	    }; }