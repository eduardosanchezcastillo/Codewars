/*
    Complex CSV Parser
    https://www.codewars.com/kata/525ca723b6aecee8c900033c

We need valid CSV parser!

A CSV (Comma-Separated Values) file is a file that represents tabular data in a simple, text-only manner. At it's
simplest, a CSV file contains rows of data separated by newlines, and each field is separated by commas.

This parser needs to not only handle CSVs using commas to delimit fields, but it also needs to handle complex field
values (which may be wrapped in quotes, and may span multiple lines), and also different delimiters and quote characters.


###Examples

A simple CSV would look like this:
    a,b,c
    d,e,f

which should parse to:
    [['a', 'b', 'c'], ['d', 'e', 'f']


While a more complex one might be:
    one,"two wraps
    onto ""two"" lines",three
    4,,6

that becomes
    [['one', 'two wraps\nonto "two" lines', 'three'], ['4', '', '6']]


##Specification:

Basics
    - The parser should return an array of arrays, one array for each row of the CSV file (not necessarily each line of
      text!).
    - Rows are delimited by the newline character ("\n").
    - Each row is divided by a separator character, by default the comma (,). All characters between separators are part
      of the value — do not trim the field value.
    - Fields are assumed to be strings — don't convert them to numbers or other types in this kata.
    - Empty fields are valid — don't discard empty values at the beginning, middle or end of a row. These should be
      included as an empty string.
    - Likewise, an empty row is still valid, and effectively contains a single empty field.
    - For this kata, expect uneven rows. Just include the actual fields in each row, even if the rows have a different
      number of fields.

Quoted Fields
    - The parser should handle quoted fields.
    - A quoted field starts and ends with the same character, and every character in between makes up the field value,
      including separator characters. The default quote character is a double quote (").
    - Quoted fields may span multiple lines — don't assume a newline means a new row!
    - Quoted fields only start immediately following a separator character, newline, or start of the file. If a quote
      character occurs anywhere else, it should be treated as a normal field value.
    - They should be immediately followed by a separator, newline, or the end of the file.
    - If a quote character occurs within a quoted field, it is simply doubled. For example, the value foo "bar" baz will
      be encoded as "foo ""bar"" baz". The parser should identify and unescape these values.
    - You should check for unclosed quoted fields, and throw an error, but this is not tested here.

Alternate Characters
    - The parser should handle alternate characters for the separator and quote.
    - You may safely assume that the values provided are a single character, but bonus points for error checking.
*/

/**
 * CSV Parser.  Takes a string as input and returns
 * an array of arrays (for each row).
 *
 * @param input String, CSV input
 * @param separator String, single character used to separate fields.
 *        Defaults to ","
 * @param quote String, single character used to quote non-simple fields.
 *        Defaults to "\"".
 */
function parseCSV(input, separator, quote) {
  const NEW_LINE = '\n';
  separator = separator || ',';
  quote = quote || '"';
  if (separator.lengh > 1) throw 'Incorrect separator';
  if (quote.lengh > 1) throw 'Incorrect quote';

  const dataHolder = {
    res: [[]],
    curr: '',
    withinQuotes: false,

    pushCurr: function() {
      this.res[this.res.length-1].push(this.curr);
      this.curr = '';
    },

    addNewLine: function() {
      this.res.push([]);
    },

    acceptChar: function(ch) {
      this.curr += ch;
    },

    toggleQuote: function() {
      this.withinQuotes = !this.withinQuotes;
    }
  };

  for (let ii = 0; ii < input.length; ii++) {
    let ch = input[ii];
    if (dataHolder.withinQuotes && ch !== quote) {
      dataHolder.acceptChar(ch);
    } else {
      switch (ch) {
        case quote:
          if (ii < input.length - 1 && input[ii+1] === quote) {
            ii++;
            if (dataHolder.withinQuotes) {
              dataHolder.acceptChar(quote);
            }
          } else {
            dataHolder.toggleQuote();
          }
          break;

        case NEW_LINE:
          dataHolder.pushCurr();
          dataHolder.addNewLine();
          break;

        case separator:
          dataHolder.pushCurr();
          break;

        default:
          dataHolder.acceptChar(ch);
      }
    }
  }

  dataHolder.pushCurr();
  if (dataHolder.withinQuotes) throw 'Unclosed quote!';

  console.log(input, '\n---\n', dataHolder.res);
  return dataHolder.res;
}