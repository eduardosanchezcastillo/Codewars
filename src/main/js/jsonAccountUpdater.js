/*
    JSON Account Updater
    https://www.codewars.com/kata/588d5898277e86e2ce000070

A system is receives each hour a batch with logon information. Can you write a method that can update accounts with the
latest logon information?

TASK:

Finish this method:
    function updateAccounts(accounts, logons)

The function accepts accounts and returns the same list updated. The list of accounts is a JSON object:
    var accounts = { "Accounts" : [
                         {
                            "Id": 21,
                            "Name": "John Shepherd",
                            "LogonCount" : 13,
                            "LastLogon" : new Date(2017, 1, 14, 16, 15, 6, 111)
                        },
                        {
                            ...
                        }]
                    }

The function accepts a list of logons that is a JSON object:
    var logons = { "Logons" : [
                         {
                            "Id": 21,
                            "Name": "John Shepherd",
                            "Date" : new Date(2017, 1, 14, 16, 15, 6, 111)
                        },
                        {
                            ...
                        }]
                    }

The updates must follow this pattern:
    - Accounts are matched with the logon information by the Id fields.
    - If an account matches a logon, The LogonCount is incremented with 1.
    - If LastLogon is older than the logon Date, it will be set to the logon date.
    - Also Name will then be updated if the logon Name is not empty.
    - If an Id is not found, a new account will be added with the LogonCount set to 1.
    - Accounts are returned ordered by Id ascending, but they are not necessarily ordered when they are passed as a parameter.
*/

function updateAccounts(accounts, logons){
	// there might be several logons by the same user and we need to apply them in chronological order
	logons.Logons.sort((a, b) => a.Date - b.Date);

	// update accounts with the logon info
	for (let i = 0; i < logons.Logons.length; i++) {
		const logon = logons.Logons[i];
		const acc = getAccountById(accounts.Accounts, logon.Id)
		if (acc) {
			acc.LogonCount++;
			if (acc.LastLogon < logon.Date) {
				acc.LastLogon = logon.Date;
				if (logon.Name) {
					acc.Name = logon.Name;
				}
			}
		} else {
			accounts.Accounts.push({
				Id: logon.Id,
				Name: logon.Name,
				LogonCount: 1,
				LastLogon: logon.Date
			});
		}
	}

	accounts.Accounts.sort((a, b) => a.Id - b.Id);

	return accounts;
}

function getAccountById(accounts, id) {
	for (let i = 0; i < accounts.length; i++) {
		if (accounts[i].Id === id) {
			return accounts[i];
		}
	}
	return null;
}