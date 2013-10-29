/**
 * @author Jun Yu
 * @license GPLv2 
 * @IDE Eclipse 4.3 
 * @Compiler JDK7
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class JUYUP6 {

	private String[] recordTable;
	private String[] keyTable;
	private int tableSize = 0;
	private int currentRecord = 0;
	private static final int DEFAULT_HASHTABLE_SIZE = 1013;

	// Accounting data
	private int insertTimes = 0;
	private int probeTimesInsert = 0;
	private int searchTimesSuccessfulSearch = 0;
	private int searchTimesUnsuccessfulSearch = 0;
	private int probeTimesSuccessfulSearch = 0;
	private int probeTimesUnsuccessfulSearch = 0;

	private int recentProbeTimes = 0;

	/**
	 * create an empty hash table of size s
	 * 
	 * @param s
	 */
	public JUYUP6(int s) {
		tableSize = s;
		recordTable = new String[tableSize];
		keyTable = new String[tableSize];
		for (int i = 0; i < recordTable.length; i++) {
			recordTable[i] = null;
		}
	}

	/**
	 * 
	 * @param key
	 *            is a string of lower case alphabetic chars w/o spaces
	 * @param record
	 * @return false if key is present, true otherwise
	 * @exception the table overflowed
	 */
	public boolean insert(String key, String record) throws ArrayIndexOutOfBoundsException {
		if (currentRecord >= tableSize)
			throw new ArrayIndexOutOfBoundsException(currentRecord + 1);

		int index = find(key);
		insertTimes++;
		probeTimesInsert += recentProbeTimes;
		recentProbeTimes = 0;
		if (keyTable[index] != null)
			return false;

		keyTable[index] = key;
		recordTable[index] = record;
		currentRecord++;
		return true;
	}

	/**
	 * 
	 * @param key
	 * @return the record if key is present, null otherwise
	 */
	public String isPresent(String key) {
		String record = recordTable[find(key)];
		if (record == null) {
			probeTimesUnsuccessfulSearch += recentProbeTimes;
			searchTimesUnsuccessfulSearch++;
		} else {
			probeTimesSuccessfulSearch += recentProbeTimes;
			searchTimesSuccessfulSearch++;
		}
		recentProbeTimes = 0;
		return record;
	}

	/**
	 * 
	 * @param key
	 * @return false if key is not present, true otherwise
	 */
	public boolean delete(String key) {
		int index = find(key);
		boolean retVal;

		if (keyTable[index] == null) {
			probeTimesUnsuccessfulSearch += recentProbeTimes;
			searchTimesUnsuccessfulSearch++;
			retVal = false;
		} else {
			keyTable[index] = null;
			recordTable[index] = null;
			probeTimesSuccessfulSearch += recentProbeTimes;
			searchTimesSuccessfulSearch++;
			currentRecord--;
			retVal = true;
		}

		recentProbeTimes = 0;
		return retVal;
	}

	/**
	 * 
	 * @return the number of records in the hash table
	 */
	public int size() {
		int numRecord = 0;
		for (String record : recordTable) {
			if (record != null)
				numRecord++;
		}
		return numRecord;
	}

	/**
	 * list all members of the hash table in the order that they are stored.
	 * Precede each one with an integer giving the index in the table of that
	 * record
	 */
	public void listAll() {
		for (int i = 0; i < recordTable.length; i++) {
			if (recordTable[i] != null)
				System.out.println(i + " " + keyTable[i] + ":" + recordTable[i]);
		}
	}

	/**
	 * prints three lines: Average probes in insert = #.# Average probes in
	 * unsuccessful search = #.# Average probes in successful search = #.# where
	 * the values are rounded to one decimal place
	 */
	public void printStats() {
		if (insertTimes == 0)
			System.out.println("No insert");
		else
			System.out.println(String.format("Average probes in insert = %.1f",
					Double.valueOf(probeTimesInsert) / insertTimes));

		if (searchTimesUnsuccessfulSearch == 0)
			System.out.println("No unsuccessful search");
		else
			System.out.println(String.format(
					"Average probes in unsuccessful search = %.1f",
					Double.valueOf(probeTimesUnsuccessfulSearch)
							/ searchTimesUnsuccessfulSearch));

		if (searchTimesSuccessfulSearch == 0)
			System.out.println("No successful search");
		else
			System.out.println(String.format(
					"Average probes in successful search = %.1f",
					Double.valueOf(probeTimesSuccessfulSearch)
							/ searchTimesSuccessfulSearch));
	}

	/**
	 * Find an empty cell or a cell that has same value of key
	 * 
	 * @param key
	 * @return the index of empty cell or present cell
	 */
	private int find(String key) {
		int hashVal = hash(key);
		int hashVal2 = hash2(hashVal);
		int i = 0;
		String testKey;
		int temp = hashVal;
		for (testKey = keyTable[temp], i = 0, recentProbeTimes++; testKey != null
				&& !testKey.equals(key); i++) {
			temp = hashVal + i * hashVal2;
			temp %= tableSize;
			if (temp < 0)
				temp += tableSize;
		}

		return temp;
	}

	private int hash(String key) {
		int hashVal = 0;

		for (int i = 0; i < key.length(); i++) {
			hashVal = 37 * hashVal + key.charAt(i);
		}

		hashVal %= tableSize;
		if (hashVal < 0)
			hashVal += tableSize;

		return hashVal;
	}

	private int hash2(int hashVal) {
		return 13 - hashVal % 13;
	}

	public void clear() {
		for (int i = 0; i < keyTable.length; i++) {
			keyTable[i] = null;
			recordTable[i] = null;
		}
		currentRecord = 0;
		insertTimes = 0;
		probeTimesInsert = 0;
		searchTimesSuccessfulSearch = 0;
		searchTimesUnsuccessfulSearch = 0;
		probeTimesSuccessfulSearch = 0;
		probeTimesUnsuccessfulSearch = 0;
		recentProbeTimes = 0;
	}

	public void usage() {
		String use = "";
		use += "N		Print your name(s) followed by a newline\n";
		use += "C		clear the hash table to empty and reset the statistics\n";
		use += "		I soap:Keeps you clean // Insert record \"Keeps you clean\" with \"soap\" as its key\n";
		use += " 		Print one of the lines \"Key soap inserted\", \"Key soap already exists\", OR\n";
		use += " 		\"Table has overflowed\". In the last case, the record isn't inserted\n";
		use += "D tin	Delete the record that has \"tin\" as a key\n";
		use += " 		Print one of the lines \"Key tin deleted\" OR \"Key tin not present\"\n";
		use += "S fort	Search for the key \"fort\"\n";
		use += " 		Print one of the lines \"Key fort:\" then the record corresponding to that key, OR\n";
		use += " 		\"Key fort not found\"\n";
		use += "Z		Print the line \"Size is n\" where n is the number of records stored in the table\n";
		use += "P		Print a list of the elements in the Table in the order of their position in the table,\n";
		use += "		one record per line in the form \"# key:Record\"\n";
		use += "T		Print the following three lines\n";
		use += " 		Average probes in insert = #.#\n";
		use += " 		Average probes in unsuccessful search = #.#\n";
		use += " 		Average probes in successful search = #.#\n";
		use += " 		where the values are rounded to one decimal place\n";
		use += "E		The end of input\n";

		System.out.print(use);
	}

	public static void main(String[] args) {
		JUYUP6 hashTable = new JUYUP6(DEFAULT_HASHTABLE_SIZE);
		hashTable.usage();
		System.out.println("");

		String cmd;
		BufferedReader input = new BufferedReader(new InputStreamReader(
				System.in));
		try {
			for (;;) {
				cmd = input.readLine();
				char option = cmd.charAt(0);
				option = Character.toUpperCase(option);
				String param;

				String key;
				String record;
				boolean success = false;

				switch (option) {
				case 'N':
					System.out.println("Jun Yu");
					break;
				case 'C':
					hashTable.clear();
					break;
				case 'I':
					param = cmd.substring(2);
					String[] array = param.split(":");
					key = array[0];
					record = array[1];
					try {
						success = hashTable.insert(key.trim(), record.trim());
					}catch (ArrayIndexOutOfBoundsException e) {
						System.out.println("Table has overflowed");
					}
					if (success == true)
						System.out.println("Key " + key + " inserted");
					else
						System.out.println("Key " + key + " already exists");
					break;
				case 'D':
					key = cmd.substring(2);
					success = hashTable.delete(key);
					if (success == true)
						System.out.println("Key " + key + " deleted");
					else
						System.out.println("Key " + key + " not present");
					break;
				case 'S':
					key = cmd.substring(2);
					record = hashTable.isPresent(key);
					if (record == null)
						System.out.println("Key " + key + " not found");
					else
						System.out.println("Key " + key + ": " + record);
					break;
				case 'Z':
					System.out.println("Size is " + hashTable.size());
					break;
				case 'P':
					hashTable.listAll();
					break;
				case 'T':
					hashTable.printStats();
					break;
				case 'E':
					return;
				default:
					System.out
							.println("Please see usage and input proper command");
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
