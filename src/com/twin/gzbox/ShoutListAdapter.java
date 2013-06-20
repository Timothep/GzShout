package com.twin.gzbox;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TextView.BufferType;

public class ShoutListAdapter extends ArrayAdapter<Shout> implements AvatarCacheListener {

	private final SimpleDateFormat sdf = new SimpleDateFormat("dd MMM - HH:mm");
	
	private Context context;
	private AvatarCache avatarCache;
	private Map<String, Integer> smileyMap = new HashMap<String, Integer>();

		
	public ShoutListAdapter(Context context, AvatarCache avatarCache) {
		super(context, R.layout.messagerow);
		this.context = context;		
		this.avatarCache = avatarCache;
		initSmileys();
		setNotifyOnChange(false);
		avatarCache.addListener(this);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.messagerow, parent, false);

		TextView txtAuthor = (TextView) rowView.findViewById(R.id.txtAuthor);
		TextView txtMessage = (TextView) rowView.findViewById(R.id.txtMessage);
		TextView txtDate = (TextView) rowView.findViewById(R.id.txtDate);
		ImageView imageView = (ImageView) rowView.findViewById(R.id.ibtnAvatar);

		Shout shout = getItem(position);
		
		txtAuthor.setText(shout.getAuthor());
		setMessage(txtMessage, shout.getText());		
		txtDate.setText(sdf.format(shout.getDate()));
		imageView.setImageBitmap(avatarCache.getAvatar(shout.getAuthor()));		
		
		return rowView;
	}
	
	private void setMessage(TextView txtMessage, String text) {
		SpannableStringBuilder ssb = new SpannableStringBuilder(text);
		// will work only for the first occurence of each smiley (good enough for now)
		for (String code : smileyMap.keySet()) {
			int index = text.indexOf(code);
			if(index > 0) {
				ssb.setSpan(new ImageSpan(context, smileyMap.get(code)), index, code.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
			}
		}			
		txtMessage.setText(ssb, BufferType.SPANNABLE);		
	}

	public void updateShoutList(List<Shout> list) {	
		clear();
		for (Shout shout : list) {
			add(shout);
		}
		notifyDataSetChanged();
		setNotifyOnChange(false);
	}

	public void onAvatarUpdated() {
		notifyDataSetChanged();
		setNotifyOnChange(false);
	}
	
	private void initSmileys() {
		smileyMap.put(":doh:",R.drawable.smi_doh);
		smileyMap.put(":jesors:",R.drawable.smi_jesors);
		smileyMap.put(":flame:",R.drawable.smi_flame);
		smileyMap.put(":drink:",R.drawable.smi_drink);
		smileyMap.put(":devil3:",R.drawable.smi_devil3);
		smileyMap.put(":devil2:",R.drawable.smi_devil2);
		smileyMap.put(":ouuiinn:",R.drawable.smi_cwm14);
		smileyMap.put(":-/",R.drawable.smi_confused);
		smileyMap.put(":cassetete:",R.drawable.smi_cassetete);
		smileyMap.put(":calimero:",R.drawable.smi_calimero);
		smileyMap.put(":gun:",R.drawable.smi_gun);
		smileyMap.put(":blamblam:",R.drawable.smi_blamblam);
		smileyMap.put(":\\(",R.drawable.smi_sad);
		smileyMap.put(";\\)",R.drawable.smi_wink);
		smileyMap.put(":ninja2:",R.drawable.smi_shuriken);
		smileyMap.put(":santa:",R.drawable.smi_santa);
		smileyMap.put(":turned:",R.drawable.smi_turned);
		smileyMap.put(":pinch:",R.drawable.smi_pinch);
		smileyMap.put(":music:",R.drawable.smi_music);
		smileyMap.put(":notify:",R.drawable.smi_notify);
		smileyMap.put(":cry:",R.drawable.smi_cry);
		smileyMap.put(":luv:",R.drawable.smi_lub);
		smileyMap.put(":rantin:",R.drawable.smi_rantin);
		smileyMap.put(":mad:",R.drawable.smi_mad);
		smileyMap.put(":phone:",R.drawable.smi_phone);
		smileyMap.put(":innocent:",R.drawable.smi_innocent);
		smileyMap.put(":yltype:",R.drawable.smi_yltype);
		smileyMap.put(":thumbs-up2:",R.drawable.smi_thumbsup);
		smileyMap.put(":thumbdown:",R.drawable.smi_thumbdown);
		smileyMap.put(":sors:",R.drawable.smi_sors);
		smileyMap.put(":smack:",R.drawable.smi_smack);
		smileyMap.put(":slicer:",R.drawable.smi_slicer);
		smileyMap.put(":sick:",R.drawable.smi_sick);
		smileyMap.put(":wassat:",R.drawable.smi_wassat);
		smileyMap.put(":tongue2:",R.drawable.smi_sb_tongue);
		smileyMap.put(":rougi:",R.drawable.smi_rougi);
		smileyMap.put(":rock:",R.drawable.smi_rock);
		smileyMap.put(":kiss:",R.drawable.smi_kiss);
		smileyMap.put(":zorro:",R.drawable.smi_zorro);
		smileyMap.put(":-",R.drawable.smi_whistling);
		smileyMap.put(":angry2:",R.drawable.smi_angry_new);
		smileyMap.put(":blink:",R.drawable.smi_blink);
		smileyMap.put(":wacko:",R.drawable.smi_wacko);
		smileyMap.put(":unsure:",R.drawable.smi_unsure);
		smileyMap.put(":wub:",R.drawable.smi_wub);
		smileyMap.put(":\\)",R.drawable.smi_smile);
		smileyMap.put("<_<",R.drawable.smi_dry);
		smileyMap.put("-_-",R.drawable.smi_sleep);
		smileyMap.put(":rolleyes:",R.drawable.smi_rolleyes);
		smileyMap.put("B\\)",R.drawable.smi_cool);
		smileyMap.put(":lol:",R.drawable.smi_laugh);
		smileyMap.put(":D",R.drawable.smi_big_grin);
		smileyMap.put(":P",R.drawable.smi_tongue);
		smileyMap.put(":o",R.drawable.smi_ohmy);
		smileyMap.put("\\^_\\^",R.drawable.smi_happy);
		smileyMap.put(":huh:",R.drawable.smi_huh);
		smileyMap.put(":mellow:",R.drawable.smi_mellow);
		smileyMap.put(":blush:",R.drawable.smi_blush);
		smileyMap.put(":closedeyes:",R.drawable.smi_closedeyes);
		smileyMap.put(":excl:",R.drawable.smi_excl);
		smileyMap.put("\\(w00t\\)",R.drawable.smi_w00t);
		smileyMap.put(":sweating:",R.drawable.smi_sweatingbullets);
		smileyMap.put(":teehee:",R.drawable.smi_stuart);
		smileyMap.put(":sorcerer:",R.drawable.smi_sorcerer);
		smileyMap.put(":zzz:",R.drawable.smi_sleeping);
		smileyMap.put(":fourbe:",R.drawable.smi_shifty);
		smileyMap.put(":geek:",R.drawable.smi_online2long);
		smileyMap.put(":nuke:",R.drawable.smi_nuke);
		smileyMap.put(":-|",R.drawable.smi_noexpression);
		smileyMap.put(":hug:",R.drawable.smi_hug);
		smileyMap.put(":ninja:",R.drawable.smi_ninja);
		smileyMap.put(":alien:",R.drawable.smi_alien);
		smileyMap.put(":pirate:",R.drawable.smi_chris);
		smileyMap.put(":crying:",R.drawable.smi_crying);
		smileyMap.put(":devil:",R.drawable.smi_devil);
		smileyMap.put(":ermm:",R.drawable.smi_ermm);
		smileyMap.put(":flowers:",R.drawable.smi_flowers);
		smileyMap.put(":angry:",R.drawable.smi_angry);
		smileyMap.put(":biggrin:",R.drawable.smi_biggrin);
	}
	
}
