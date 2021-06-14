module AbstractSyn

type typ = 
  | TypInt
  | TypChar
  | TypString
  | TypFloat
  | TypVoid
  | TypVoid
  | TypStruct of string
  | TypArray of typ * int option
  | TpyPoint of typ
  | Lambda of typ option * (typ * string) list * IStatement
 
and expr =
  | Access of access
  | Assign of access * expr
  | Addr of access
  | ConstInt of int
  | ConstFloat of float
  | ConstString of string
  | ConstChar of char
  | ConstNull of int
  | NullExpression of int
  | UnaryPrim of string * expr
  | BinaryPrim of string * expr * expr
  | TernaryPrim of expr * expr * expr
  | Andalso of expr * expr
  | Orelse of expr * expr
  | Call of string * expr list

and access = 
  | AccVar of string
  | AccDeref of expr
  | AccIndex of access * expr
  | AccMember of access * access

and statement = 
  | If of expr * statement * statement
  | While of expr * statement
  | DoWhile of statement * expr
  | Expr of expr
  | Return of expr option
  | Block of statementDec list
  | For of expr * expr * expr * statement
  | Loop of statement
  | Case of expr * statement
  | Switch of expr * statement list
  | Range of expr * expr * expr * statement
  | Throw of expr
  | Try of statement * statement list
  | Catch of exception * statement
  | Break
  | Continue

and exception = 
  | Exp of string

and statementDec = 
  | Dec of typ * string
  | DecAsg of typ * string * exception
  | Stmt of statement

and topDec = 
  | FunDec of typ option * string * (typ * string) list * statement
  | VarDec of typ * string
  | StructDec of string * (typ * string) list
  | VarDecAsg of typ * string * exception
 
and program = 
  | Prog of topDec list