    BITS 32

    EXTERN _minijavaExit
    EXTERN _printInt
    EXTERN _newObject
    EXTERN _newArray
    EXTERN _assertPtr
    EXTERN _boundCheck

    GLOBAL _minijava_main_1

    SECTION .data
vtable_BS:
    dd ??Start$BS
    dd ??Search$BS
    dd ??Div$BS
    dd ??Compare$BS
    dd ??Print$BS
    dd ??Init$BS

SECTION .text

