#pragma once
#include "stdafx.h""
#include <string>
#include <ctime>
#include <iostream>
#include <iomanip>

using namespace std;
class Coin
{
private:
	string chance;
public:
	Coin();
	void toss();
	string getSide();
};
Coin::Coin() //default constructor
{
	int val = rand() % 2;
	if (val == 0)
		chance = "Heads";
	else{
		chance = "Tails";
	}
}

void Coin::toss() 
{
	int val = rand() % 2;
	if (val == 0)
		chance = "Heads";
	else{
		chance = "Tails";
	}
}
string Coin::getSide()
{
	return chance;
}

int main()
{
	srand(time(NULL));
	Coin coins[3];
	double balance = 0;
	cout << setprecision(2) << fixed;

	for (int i = 1; balance < 1; i++)
	{
		cout << "Round " << i << endl
			<< "Balance = " << balance << endl;

		for (int j = 0; j < 3; j++){ //process 
			coins[j].toss();
		}
		cout << "Nickle = " << coins[0].getSide() << endl << "Dime = " << coins[1].getSide() << endl << "Quarter = " << coins[2].getSide() << endl;

		if (coins[0].getSide() == "Heads")
			balance += 0.05;
		if (coins[1].getSide() == "Heads")
			balance += 0.1;
		if (coins[2].getSide() == "Heads")
			balance += 0.25;
		cout << right << setw(50) << "TOTAL BALANCE: " << setw(5) << balance << endl;
	}
	if (balance == 1)
		cout << "YOU WON!" << endl;
	else{
		cout << "YOU LOST" << endl;
	}
	system("pause");
}
