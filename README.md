# Test_console_app
The program counts the number of files in a folders and all subfolders.
The folders in which you want to perform the count are specified in the file, which passed as a first argument to the command line.
The counting operation is performed simultaneously for each folder from input data file.
The results are recorded after end of the counting in their folder to the file, which transferred as the second
argument of the command line and displayed on the screen.
The input file contains one or more paths lines. Each of the lines is a folder that requires counting the files in it.
The output file is in CSV format with a semicolon delimiter.
The first column is the source path from input file, the second is the number of files in the folder.
The output to the screen is presented in the tabular form.
The first column is the serial number of the file, the second column is the number of files found,
the third column is the original path for counting.
The user has the option to cancel running calculations by pressing the Esc key.
At the same time the results of the data collected at that moment displayed on the screen.