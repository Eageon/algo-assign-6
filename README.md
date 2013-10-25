algo-assign-6
=============

Algorithm assignment 6


CS 5343.002 Algorithm analysis and Data Structures
Fall 2013
Wed, Oct 16
Assignment #6  (optional)

Due 2 AM, Wednesday, Oct 30 on eLearning

Instructions: This is an optional programming assignment (individual or group). 
Answers should be your own work.  You can (re)use any code from the text book.


Write a class to implement a hash table that uses double hashing,
together with a main section to test it.

The member functions are:
DoubleHash(int s);		
// create an empty hash table of size s

boolean insert(String key, String record);
// returns false if key is present, true otherwise.
// key is a string of lower case alphabetic chars w/o spaces.

String isPresent(String key);
// returns the record if key is present, null otherwise

boolean delete(String key);
// returns false if key is not present, true otherwise

int size();
// returns the number of records in the hash table

void listAll();
// list all members of the hash table in the order that
// they are stored. Precede each one with an integer giving
// the index in the table of that record

void printStats();
// prints three lines:
// Average probes in insert = #.#
// Average probes in unsuccessful search = #.#
// Average probes in successful search = #.#
// where the values are rounded to one decimal place.

Use the hash function in Fig. 5.4 in the book, with the caption
"A good hash function".  Use 1013 as the size of the table.
Use h_2(x) = 13 - (x mod 13) as the second function for double hashing.
Your program reads text commands from Stdin (System.in).  In response to
each command your program should output one or more lines of text
to Stdout (System.out).  Sample input and output files will be provided later.

Commands
--------
N	// Print your name(s) followed by a newline
C	// clear the hash table to empty and reset the statistics
I soap:Keeps you clean // Insert record "Keeps you clean" with "soap" as its key
	// Print one of the lines "Key soap inserted", "Key soap already exists", OR
	// "Table has overflowed". In the last case, the record isn't inserted
D tin	// Delete the record that has "tin" as a key
	// Print one of the lines "Key tin deleted" OR "Key tin not present"
S fort	// Search for the key "fort"
	// Print one of the lines "Key fort:" then the record corresponding to that key, OR
	// "Key fort not found"
Z	// Print the line "Size is n" where n is the number of records stored in the table
P	// Print a list of the elements in the Table in the order of their position in the table,
	// one record per line in the form "# key:Record"
T	// Print the following three lines
	// Average probes in insert = #.#
	// Average probes in unsuccessful search = #.#
	// Average probes in successful search = #.#
	// where the values are rounded to one decimal place
E	// The end of input 


RULES FOR PROGRAMMING AND SUBMISSION:

1. Write your program as one source file and do not use the
   "package" construct in your Java source.

2. Name your source file (and your main class if you use Java)
   by the first two characters of your first name, followed by
   first two characters of your last name (4 upper case characters)
   followed by the string "P6". e.g.: BARAP6.java for Balaji Raghavachari.

3. Your program must not output any prompts. Its output must
   exactly match the format given in the specification.

4. Do not use any Java Collection Classes, except Strings and arrays.

5. Include a comment at the top of your source file giving
   your name(s) and the IDE/compiler that you used.

6. You program must compile and run in a DOS window or a UNIX
   terminal window using file redirection.

7. Use good style and layout. Comment your code well.

8. Your program MUST read from Stdin (System.in) and write to
   Stdout (System.out). Use file redirection to test your program.

9. Submit ONE source code file on eLearning.  Don't submit a
   compressed file. Don't email your submission.
