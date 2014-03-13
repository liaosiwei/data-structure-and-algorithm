#include <iostream>
#include <vector>
#include <algorithm>
#include <numeric>
/* run this program using the console pauser or add your own getch, system("pause") or input loop */
using namespace std;

vector<int> vec = {5, 2, 1, 9, 5};

int main(int argc, char** argv) {
	int s = accumulate(vec.begin(),vec.end(), 0);
	vector<vector<int>> P;
	int inner_size = *max_element(vec.begin(), vec.end(), [](int a, int b){return a < b;})*vec.size()+1;
	for(auto i = 0; i <= vec.size(); i++) {
		vector<int> temp(inner_size);
		P.push_back(temp);
	}
	
	for (auto i = 1; i <= vec.size(); i++) {
		for (auto j = 0; j < P[0].size(); j++) {
			if (vec[i-1] > j) P[i][j] = P[i-1][j];
			else if (vec[i-1] == j) P[i][j] = 1;
			else P[i][j] = max(P[i-1][j], P[i-1][j-vec[i-1]]);
		}
	}
	
	for(auto i = s / 2; i > 0; i--) {
		if (P[vec.size()][i] == 1) {
			cout << "min of the two sets is " << s - 2*i << endl;
			break;
		}
	}
}
