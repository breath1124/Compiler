// Signature file for parser generated by fsyacc
module Par
type token = 
  | EOF
  | LPAR
  | RPAR
  | LBRACE
  | RBRACE
  | LBRACK
  | RBRACK
  | SEMI
  | COMMA
  | ASSIGN
  | AMP
  | QUEST
  | COLON
  | NOT
  | SEQOR
  | SEQAND
  | EQ
  | NE
  | GT
  | LT
  | GE
  | LE
  | PLUS
  | MINUS
  | TIMES
  | DIV
  | MOD
  | AUTOPLUS
  | AUTOMINUS
  | CHAR
  | ELSE
  | IF
  | INT
  | NULL
  | PRINT
  | PRINTLN
  | RETURN
  | VOID
  | WHILE
  | FLOAT
  | SWITCH
  | CASE
  | DEFAULT
  | FOR
  | IN
  | RANGE
  | STRING
  | DO
  | LOOP
  | CONTINUE
  | BREAK
  | CSTCHAR of (char)
  | CSTFLOAT of (float32)
  | CSTSTRING of (string)
  | NAME of (string)
  | CSTINT of (int)
  | CSTBOOL of (int)
type tokenId = 
    | TOKEN_EOF
    | TOKEN_LPAR
    | TOKEN_RPAR
    | TOKEN_LBRACE
    | TOKEN_RBRACE
    | TOKEN_LBRACK
    | TOKEN_RBRACK
    | TOKEN_SEMI
    | TOKEN_COMMA
    | TOKEN_ASSIGN
    | TOKEN_AMP
    | TOKEN_QUEST
    | TOKEN_COLON
    | TOKEN_NOT
    | TOKEN_SEQOR
    | TOKEN_SEQAND
    | TOKEN_EQ
    | TOKEN_NE
    | TOKEN_GT
    | TOKEN_LT
    | TOKEN_GE
    | TOKEN_LE
    | TOKEN_PLUS
    | TOKEN_MINUS
    | TOKEN_TIMES
    | TOKEN_DIV
    | TOKEN_MOD
    | TOKEN_AUTOPLUS
    | TOKEN_AUTOMINUS
    | TOKEN_CHAR
    | TOKEN_ELSE
    | TOKEN_IF
    | TOKEN_INT
    | TOKEN_NULL
    | TOKEN_PRINT
    | TOKEN_PRINTLN
    | TOKEN_RETURN
    | TOKEN_VOID
    | TOKEN_WHILE
    | TOKEN_FLOAT
    | TOKEN_SWITCH
    | TOKEN_CASE
    | TOKEN_DEFAULT
    | TOKEN_FOR
    | TOKEN_IN
    | TOKEN_RANGE
    | TOKEN_STRING
    | TOKEN_DO
    | TOKEN_LOOP
    | TOKEN_CONTINUE
    | TOKEN_BREAK
    | TOKEN_CSTCHAR
    | TOKEN_CSTFLOAT
    | TOKEN_CSTSTRING
    | TOKEN_NAME
    | TOKEN_CSTINT
    | TOKEN_CSTBOOL
    | TOKEN_end_of_input
    | TOKEN_error
type nonTerminalId = 
    | NONTERM__startMain
    | NONTERM_Main
    | NONTERM_TopDecs
    | NONTERM_TopDec
    | NONTERM_VarDec
    | NONTERM_VarDecAsg
    | NONTERM_VarDescribe
    | NONTERM_FunDec
    | NONTERM_ParamDecs
    | NONTERM_ParamNotEmptyDecs
    | NONTERM_Block
    | NONTERM_StmtOrDecSeq
    | NONTERM_Stmt
    | NONTERM_StmtM
    | NONTERM_StmtCase
    | NONTERM_StmtU
    | NONTERM_Expr
    | NONTERM_ExprNotAccess
    | NONTERM_AtExprNotAccess
    | NONTERM_Access
    | NONTERM_Exprs
    | NONTERM_Exprs1
    | NONTERM_Const
    | NONTERM_ConstString
    | NONTERM_ConstFloat
    | NONTERM_ConstChar
    | NONTERM_Type
/// This function maps tokens to integer indexes
val tagOfToken: token -> int

/// This function maps integer indexes to symbolic token ids
val tokenTagToTokenId: int -> tokenId

/// This function maps production indexes returned in syntax errors to strings representing the non terminal that would be produced by that production
val prodIdxToNonTerminal: int -> nonTerminalId

/// This function gets the name of a token as a string
val token_to_string: token -> string
val Main : (FSharp.Text.Lexing.LexBuffer<'cty> -> token) -> FSharp.Text.Lexing.LexBuffer<'cty> -> (AbstractSyn.program) 
