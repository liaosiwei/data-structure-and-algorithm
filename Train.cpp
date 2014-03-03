#include <string.h>
#include <stdlib.h>
#include <stdio.h>
#include <iostream>
#include <fstream>
#include <sstream>
 
#define MAX(A,B) (((A)>(B))? (A) : (B))
 
using namespace std;

char * lcs(const char *a,const char * b) {
    int lena = strlen(a)+1;
    int lenb = strlen(b)+1;
 
    int bufrlen = 34000;
    char bufr[34000], *result;
 
    int i,j;
    const char *x, *y;
    int *la = (int *)calloc(lena*lenb, sizeof( int));
    int  **lengths = (int **)malloc( lena*sizeof( int*));
    for (i=0; i<lena; i++) lengths[i] = la + i*lenb;
 
    for (i=0,x=a; *x; i++, x++) {
        for (j=0,y=b; *y; j++,y++ ) {
            if (*x == *y) {
               lengths[i+1][j+1] = lengths[i][j] +1;
            }
            else {
               int ml = MAX(lengths[i+1][j], lengths[i][j+1]);
               lengths[i+1][j+1] = ml;
            }
        }
    }
 
    result = bufr+bufrlen;
    *--result = '\0';
    i = lena-1; j = lenb-1;
    while ( (i>0) && (j>0) ) {
        if (lengths[i][j] == lengths[i-1][j])  i -= 1;
        else if (lengths[i][j] == lengths[i][j-1]) j-= 1;
        else {
//			assert( a[i-1] == b[j-1]);
            *--result = a[i-1];
            i-=1; j-=1;
        }
    }
    free(la); free(lengths);
    return strdup(result);
}

//int main()
//{
//    printf("%s\n", lcs("thisisatest", "testing123testing")); // tsitest
//    printf("%s\n", lcs("abcdefg", "hijklmn"));
//    return 0;
//}

int main(int argc, char** argv) {
	ifstream inputfile("C:\\Users\\siwei\\Downloads\\input20.txt");
	ofstream outputfile("E:\\outtrain.txt");
	if ((!inputfile) || (!outputfile)) {
		cout << "open file error" << endl;
		return -1;
	}
	int k = 1;
	do {
		cout << "processing line " << k++ << endl;
		string str1, str2;
		getline(inputfile, str1);

		if (str1.compare(";") != 0) {
			getline(inputfile, str2);
			string res(lcs(str1.c_str(), str2.c_str()));
			outputfile << str1.length() + str2.length() - 2*res.length() << endl;
		}
		else {
			outputfile << ";" << '\n';
		}
		
	}
	while (!inputfile.eof());
	cout << "well done!" << endl;
}
