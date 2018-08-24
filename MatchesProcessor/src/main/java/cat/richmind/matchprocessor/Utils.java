package cat.richmind.matchprocessor;

public class Utils {
	public enum Constants {
		WIN(0), LOSS(1);
		
		private int value;
		
		private Constants(int i) { value = i; }
		
		public int valueOf() {
			return value;
		}
	}
	
}