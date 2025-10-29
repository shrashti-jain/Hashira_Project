# Hashira Campus Assessment – Polynomial Constant Finder

This project solves the **Hashira Placement Assessment** problem.  
It reads polynomial roots from a **JSON file**, converts their values from various bases,  
and finds the **constant term (C)** of the polynomial using **Lagrange interpolation**.

---

## 🧮 Problem Description

The JSON file contains:
- A `keys` object with:
  - `n`: total number of roots provided
  - `k`: minimum number of roots required to solve the polynomial
- Other entries where:
  - Each key represents the x-coordinate (root index)
  - Each value has:
    - `base`: the number base of the value
    - `value`: the actual number in that base
   
## Step-By-Step Solution

### Step 1 — Parse JSON input
- Read the JSON file and extract:
  - `n` — total number of roots provided.
  - `k` — minimum number of roots required to reconstruct the polynomial (`k = degree + 1`).
- Extract the remaining entries where each key is an `x` (the x-coordinate) and the value is an object with `"base"` and `"value"` (the y-value in that base).

### Step 2 — Decode y-values
- For each `(x, { base, value })` entry:
  - Convert `value` from the given `base` into a decimal number (use `BigInteger` in Java for very large numbers).
  - Store the decoded pairs as `(x, y_decimal)`.

### Step 3 — Reconstruct the polynomial using Lagrange interpolation
- Use the first `k` decoded points `(x_i, y_i)` to build the polynomial of degree `k - 1`.
- Compute the product and division exactly (use integer arithmetic with `BigInteger` where needed), sum the terms, and the result is the polynomial constant `C`.
