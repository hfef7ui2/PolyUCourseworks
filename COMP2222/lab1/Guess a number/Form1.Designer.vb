<Global.Microsoft.VisualBasic.CompilerServices.DesignerGenerated()> _
Partial Class Form1
    Inherits System.Windows.Forms.Form

    'Form 重写 Dispose，以清理组件列表。
    <System.Diagnostics.DebuggerNonUserCode()> _
    Protected Overrides Sub Dispose(ByVal disposing As Boolean)
        Try
            If disposing AndAlso components IsNot Nothing Then
                components.Dispose()
            End If
        Finally
            MyBase.Dispose(disposing)
        End Try
    End Sub

    'Windows 窗体设计器所必需的
    Private components As System.ComponentModel.IContainer

    '注意: 以下过程是 Windows 窗体设计器所必需的
    '可以使用 Windows 窗体设计器修改它。  
    '不要使用代码编辑器修改它。
    <System.Diagnostics.DebuggerStepThrough()> _
    Private Sub InitializeComponent()
        Me.Label = New System.Windows.Forms.Label()
        Me.inputNumber = New System.Windows.Forms.TextBox()
        Me.Button = New System.Windows.Forms.Button()
        Me.outputListBox = New System.Windows.Forms.ListBox()
        Me.SuspendLayout()
        '
        'Label
        '
        Me.Label.AutoSize = True
        Me.Label.Location = New System.Drawing.Point(21, 25)
        Me.Label.Name = "Label"
        Me.Label.Size = New System.Drawing.Size(239, 12)
        Me.Label.TabIndex = 0
        Me.Label.Text = "Please enter a number between 1 and 100"
        '
        'inputNumber
        '
        Me.inputNumber.Location = New System.Drawing.Point(91, 49)
        Me.inputNumber.Name = "inputNumber"
        Me.inputNumber.Size = New System.Drawing.Size(100, 21)
        Me.inputNumber.TabIndex = 1
        '
        'Button
        '
        Me.Button.Location = New System.Drawing.Point(100, 76)
        Me.Button.Name = "Button"
        Me.Button.Size = New System.Drawing.Size(75, 23)
        Me.Button.TabIndex = 2
        Me.Button.Text = "Guess"
        Me.Button.UseVisualStyleBackColor = True
        '
        'outputListBox
        '
        Me.outputListBox.FormattingEnabled = True
        Me.outputListBox.ItemHeight = 12
        Me.outputListBox.Location = New System.Drawing.Point(23, 105)
        Me.outputListBox.Name = "outputListBox"
        Me.outputListBox.Size = New System.Drawing.Size(237, 148)
        Me.outputListBox.TabIndex = 3
        '
        'Form1
        '
        Me.AutoScaleDimensions = New System.Drawing.SizeF(6.0!, 12.0!)
        Me.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font
        Me.ClientSize = New System.Drawing.Size(284, 261)
        Me.Controls.Add(Me.outputListBox)
        Me.Controls.Add(Me.Button)
        Me.Controls.Add(Me.inputNumber)
        Me.Controls.Add(Me.Label)
        Me.Name = "Form1"
        Me.Text = "Guess a number"
        Me.ResumeLayout(False)
        Me.PerformLayout()

    End Sub

    Friend WithEvents Label As Label
    Friend WithEvents inputNumber As TextBox
    Friend WithEvents Button As Button
    Friend WithEvents outputListBox As ListBox
End Class
