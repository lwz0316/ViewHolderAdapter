ViewHolderAdapter
=================

该适配器是通过 ViewHolder 来缓存AdapterView 中视图的适配器，通过简单的封装来达到 AdapterView 性能的提升。


# 如何使用

就是如此简单

	class LampAdapter extends ViewHolderAdapter<Lamp> {

		public LampAdapter(Context context, List<Lamp> data, int layoutRes) {
			super(context, data, layoutRes);
		}

		@Override
		protected void bindData(int pos, View convertView, final Lamp itemData) {
			// get view from ViewHolder			
			TextView type = getViewFromHolder(convertView, R.id.title);
			final CheckBox checkBox= getViewFromHolder(convertView, R.id.checkBox);
			
			type.setText(itemData.getType());
			checkBox.setChecked(itemData.isTurnon());
			updateCheckBoxStatus(checkBox, itemData.isTurnon());
			
			checkBox.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					itemData.setTurnon(!itemData.isTurnon());
					updateCheckBoxStatus(checkBox, itemData.isTurnon());
				}
			});
		}
		
		private void updateCheckBoxStatus(CheckBox checkBox, boolean isTurnOn) {
			checkBox.setText( isTurnOn ? "ON" : "OFF" );
		}
	}



