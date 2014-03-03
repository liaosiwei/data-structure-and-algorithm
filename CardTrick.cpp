#include <iostream>
#include <vector>
#include <fstream>
#include <algorithm>
#include <sstream>

using namespace std;

bool mycompare(pair<int, int> a, pair<int, int> b) {
	return a.first < b.first;
}

void output(vector<pair<int, int> > &a, ofstream &out) {
	
	sort(a.begin(), a.end(), mycompare);
	int start = 0;
	int max_len = 0, temp_len = 1;

	for (size_t i = 1; i < a.size(); i++) {
		if (a[i].second > a[i-1].second) temp_len++;
		else {
			if (temp_len > max_len) {
				max_len = temp_len;
				start = i - max_len;
			}
			temp_len = 1;
		}
	}
	if (temp_len > max_len) {
		max_len = temp_len;
		start = a.size() - max_len;
	}

	out << a.size() - max_len << endl;
	for (int i = start-1; i >= 0; i--) out << a[i].first << "B" << endl;
	for (int i = start+max_len; i < a.size(); i++) out << a[i].first << "T" << endl;
}


int main(int argc, char** argv) {
	ifstream inputfile("C:\\Users\\siwei\\Downloads\\input19.txt");
	ofstream outputfile("E:\\output.txt");
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
			vector<pair<int, int> > invec;
			istringstream ist(str);
			int one;
			int count = 0;
			while (ist >> one) {
				invec.push_back(make_pair(one, count));
				count++;
			}
			output(invec, outputfile);
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










