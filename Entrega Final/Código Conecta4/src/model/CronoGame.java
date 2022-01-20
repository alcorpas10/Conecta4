package model;

import utils.Time;

public class CronoGame 
{
		private Time time;
		private boolean playwithCrono;
		
		CronoGame(Time t) {
			this.time = t;
			playwithCrono = false;
		}
		
		void toggleCrono(boolean c) {
			playwithCrono = c;
		}
		
		boolean finishedTime() {
			if(!playwithCrono) return false;
			else return time.stopped();
		}
		
		void changeTime(Time t) {
			time = t;
		}
		
		void restart() {
			time.restart();
		}

		public int getSeconds() {
			return time.getSec() + time.getMinutes()*60;
		}
}
