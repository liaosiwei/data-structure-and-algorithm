#include <iostream>
#include <vector>
#include <string>
#include <fstream>
#include <algorithm>

using namespace std;

void convert(vector<char> &a, vector<char> &b) {
	for (size_t i = 0; i < a.size(); i++) {
		if (a[i]+13 > 'Z') b.push_back(a[i]-13);
		else b.push_back(a[i]+13);
	}
	reverse(b.begin(), b.end());
}

int main(int argc, char** argv) {
	ifstream inputfile("C:\\Users\\siwei\\Downloads\\input21.txt");
	ofstream outputfile("E:\\outgenes.txt");
	if ((!inputfile) || (!outputfile)) {
		cout << "open file error" << endl;
		return -1;
	}
	int k = 1;
	do {
		cout << "processing line " << k++ << endl;
		string str;
		getline(inputfile, str);
		if (str.empty()) continue;
		if (str.compare(";") != 0) {
			vector<char> invec(str.begin(), str.end());
			vector<char> outvec;
			outvec.reserve(20000);
			convert(invec, outvec);
			int max_len = 0, inbegin = -1, outbegin = -1;

			for(size_t i = 0; i < invec.size(); i++) {
				int k = i, count = 0, temp_len = 0;
				for (size_t j = 0; j < outvec.size(); j++) {
					if (outvec[j] == invec[k]) {
						temp_len++;
						k++;
						if (max_len < temp_len) {
							max_len = temp_len;
							inbegin = i;
							outbegin = j;
						}
					}
					else {
						if (k != i) {
							count++;
						}
						if (count > 2) {
							k = i;
							count = 0;
							temp_len = 0;	
						}
					}
				}
			}
			if (max_len == 0) outputfile << "-" << endl;
			else {
				for(size_t i = 0; i < max_len; i++)
					outputfile << invec[inbegin+i];
				outputfile << endl;
				outputfile << inbegin+1 << endl << outvec.size()-outbegin << endl;
			}

		}
		else {
			outputfile << ";" << endl;
		}
		
	}
	while (!inputfile.eof());
	inputfile.close();
	outputfile.close();
	cout << "well done!" << endl;
}

